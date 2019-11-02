package edu.ncsu.csc540.health;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import com.google.inject.persist.Transactional;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.ncsu.csc540.health.db.JdbiTransactionInterceptor;
import edu.ncsu.csc540.health.db.UnitOfWork;
import edu.ncsu.csc540.health.pages.HomePage;
import edu.ncsu.csc540.health.pages.SignInPage;
import edu.ncsu.csc540.health.pages.SignUpPage;
import org.apache.commons.configuration2.Configuration;
import org.beryx.textio.TextIO;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.function.Consumer;

public class HealthModule extends AbstractModule {
    private final Configuration configuration;

    public HealthModule(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(new TypeLiteral<Consumer<TextIO>>(){})
                .annotatedWith(Names.named("home"))
                .to(HomePage.class);

        bind(new TypeLiteral<Consumer<TextIO>>(){})
                .annotatedWith(Names.named("signUp"))
                .to(SignUpPage.class);

        bind(new TypeLiteral<Consumer<TextIO>>(){})
                .annotatedWith(Names.named("signIn"))
                .to(SignInPage.class);

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
