package edu.ncsu.csc540.health.db;

import com.google.common.base.Preconditions;

import javax.inject.Provider;
import javax.inject.Singleton;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.SharedHandle;

@Singleton
public class UnitOfWork implements Provider<Handle> {
    private ThreadLocal<SharedHandle> threadLocalHandle = new ThreadLocal<SharedHandle>();

    @Override
    public Handle get() {
        return threadLocalHandle.get();
    }

    public Boolean inUnitOfWork() {
        return threadLocalHandle.get() != null;
    }

    public Handle begin(Jdbi jdbi) {
        Preconditions.checkState(null == threadLocalHandle.get(),
                "Work already begun on this thread. Looks like you have called UnitOfWork.begin() twice" +
                        " without a balancing call to end() in between.");

        Handle handle = jdbi.open();
        // Create an instance of SharedHandle that will ignore calls to close()
        SharedHandle sharedHandle = new SharedHandle(
                handle.getJdbi(),
                handle.getConfig(),
                jdbi.getTransactionHandler(),
                handle.getStatementBuilder(),
                handle.getConnection());
        threadLocalHandle.set(sharedHandle);
        return sharedHandle;
    }

    public void end() {
        // Let's not penalize users for calling end() multiple times.
        if (threadLocalHandle.get() == null) {
            return;
        }

        SharedHandle handle = threadLocalHandle.get();

        try {
            handle.closeForReal();
        } finally {
            threadLocalHandle.remove();
        }
    }
}
