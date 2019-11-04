package edu.ncsu.csc540.health.db;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;

@Singleton
public class JdbiTransactionInterceptor implements MethodInterceptor {

    // Tracks if the unit of work was begun implicitly by this transaction.
    private ThreadLocal<Boolean> didWeStartWork = new ThreadLocal<>();

    private final UnitOfWork unitOfWork;
    private final Jdbi jdbi;

    @Transactional
    private static class Internal {}

    public JdbiTransactionInterceptor(UnitOfWork unitOfWork, Jdbi jdbi) {
        this.unitOfWork = unitOfWork;
        this.jdbi = jdbi;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // Should we start a unit of work?
        if (!unitOfWork.inUnitOfWork()) {
            unitOfWork.begin(jdbi);
            didWeStartWork.set(true);
        }

        Transactional transactional = readTransactionMetadata(methodInvocation);
        Handle handle = unitOfWork.get();

        // Allow 'joining' of transactions if there is an enclosing @Transactional method.
        if (handle.isInTransaction()) {
            return methodInvocation.proceed();
        }

        handle.begin();
        Object result;

        try {
            result = methodInvocation.proceed();
        } catch (Exception e) {
            // commit transaction only if rollback didn't occur
            if (rollbackIfNecessary(transactional, e, handle)) {
                handle.commit();
            }

            // propagate whatever exception is thrown anyway
            throw e;
        } finally {
            // Close the handle if necessary (guarded so this code doesn't run unless catch fired).
            if (didWeStartWork.get() != null && !handle.isInTransaction()) {
                didWeStartWork.remove();
                unitOfWork.end();
            }
        }

        // everything was normal so commit the txn (do not move into try block above as it
        // interferes with the advised method's throwing semantics)
        try {
            handle.commit();
        } finally {
            // close the handle if necessary
            if (didWeStartWork.get() != null) {
                didWeStartWork.remove();
                unitOfWork.end();
            }
        }

        // or return result
        return result;
    }

    private Transactional readTransactionMetadata(MethodInvocation methodInvocation) {
        Method method = methodInvocation.getMethod();
        Class targetClass = methodInvocation.getThis().getClass();

        Transactional transactional = method.getAnnotation(Transactional.class);
        if (transactional == null) {
            // If none on method, try the class.
            transactional = (Transactional) targetClass.getAnnotation(Transactional.class);
        }

        if (transactional == null) {
            // If there is no transactional annotation present, use the default
            transactional = Internal.class.getAnnotation(Transactional.class);
        }

        return transactional;
    }

    private Boolean rollbackIfNecessary(Transactional transactional, Exception e, Handle handle) {
        boolean commit = true;

        // check rollback clauses
        for (Class rollBackOn : transactional.rollbackOn()) {

            // if one matched, try to perform a rollback
            if (rollBackOn.isInstance(e)) {
                commit = false;

                // check ignore clauses (supercedes rollback clause)
                for (Class exceptOn : transactional.ignore()) {
                    // An exception to the rollback clause was found, DON'T rollback
                    // (i.e. commit and throw anyway)
                    if (exceptOn.isInstance(e)) {
                        commit = true;
                        break;
                    }
                }

                // rollback only if nothing matched the ignore check
                if (!commit) {
                    handle.rollback();
                }
                // otherwise continue to commit

                break;
            }
        }

        return commit;
    }
}
