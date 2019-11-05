package edu.ncsu.csc540.health;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import com.google.inject.persist.Transactional;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.ncsu.csc540.health.actions.ActionFactory;
import edu.ncsu.csc540.health.actions.CheckInPage;
import edu.ncsu.csc540.health.db.JdbiTransactionInterceptor;
import edu.ncsu.csc540.health.db.UnitOfWork;
import edu.ncsu.csc540.health.actions.DemoQueryPage;
import edu.ncsu.csc540.health.actions.HomePage;
import edu.ncsu.csc540.health.actions.Action;
import edu.ncsu.csc540.health.actions.SignInPage;
import edu.ncsu.csc540.health.actions.SignUpPage;
import edu.ncsu.csc540.health.actions.StaffMenuPage;
import org.apache.commons.configuration2.Configuration;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class HealthModule extends AbstractModule {
    private final Configuration configuration;

    public HealthModule(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(Action.class)
                .annotatedWith(Names.named("home"))
                .to(HomePage.class);

        bind(Action.class)
                .annotatedWith(Names.named("signUp"))
                .to(SignUpPage.class);

        bind(Action.class)
                .annotatedWith(Names.named("signIn"))
                .to(SignInPage.class);

        bind(Action.class)
                .annotatedWith(Names.named("checkIn"))
                .to(CheckInPage.class);

        bind(Action.class)
                .annotatedWith(Names.named("demo"))
                .to(DemoQueryPage.class);

        install(new FactoryModuleBuilder()
                .build(ActionFactory.class));

        configureJdbi();
    }

    private void configureJdbi() {
        HikariConfig dsConfig = new HikariConfig();
        dsConfig.setDriverClassName(configuration.getString("db.driver"));
        dsConfig.setJdbcUrl(configuration.getString("db.url"));
        dsConfig.setUsername(configuration.getString("db.user"));
        dsConfig.setPassword(configuration.getString("db.password"));

        HikariDataSource ds = new HikariDataSource(dsConfig);

        Jdbi jdbi = Jdbi.create(ds);
        jdbi.installPlugin(new SqlObjectPlugin());

        UnitOfWork unitOfWork = new UnitOfWork();
        bind(UnitOfWork.class).toInstance(unitOfWork);
        bind(Jdbi.class).toInstance(jdbi);

        JdbiTransactionInterceptor txnInterceptor = new JdbiTransactionInterceptor(unitOfWork, jdbi);
        bindInterceptor(Matchers.annotatedWith(Transactional.class), Matchers.any(), txnInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class), txnInterceptor);
    }
}
