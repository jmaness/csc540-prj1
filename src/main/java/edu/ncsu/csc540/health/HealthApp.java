package edu.ncsu.csc540.health;

import edu.ncsu.csc540.health.config.HealthEnvironmentConfiguration;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HealthApp {

    private static final Logger logger = LoggerFactory.getLogger(HealthApp.class);

    private void run(URL configFile) {
        try {
            Configuration config = loadConfiguration(configFile);
            runDatabaseMigration(config);
        } catch (ConfigurationException e) {
            logger.error(String.format("Unable to load configuration file %s", configFile), e);
        }
    }

    private Configuration loadConfiguration(URL configFile) throws ConfigurationException {
        CompositeConfiguration config = new CompositeConfiguration();
        config.addConfiguration(new HealthEnvironmentConfiguration());
        config.addConfiguration(new FileBasedConfigurationBuilder<>(YAMLConfiguration.class)
                .configure(new Parameters().hierarchical().setURL(configFile))
                .getConfiguration());

        return config;
    }

    /**
     * Runs the Flyway database schema migration for the database referenced by the
     * specified Configuration.
     *
     * @param config Configuration
     */
    private void runDatabaseMigration(Configuration config) {
        Map<String, String> properties = new HashMap<>();
        properties.put("flyway.driver", config.getString("db.driver"));
        properties.put("flyway.url", config.getString("db.url"));
        properties.put("flyway.user", config.getString("db.user"));
        properties.put("flyway.password", config.getString("db.password"));

        Flyway flyway = new Flyway();
        flyway.configure(properties);
        //flyway.baseline();
        flyway.migrate();
    }

    /**
     * Starts the Health application.
     *
     * @param args application argument
     */
    public static void main(String[] args) {
        File configFile = new File("src/main/dist/config.yml");  // default path for local development

        if (args.length >= 1) {
            configFile = new File(args[0]);
        }

        try {
            new HealthApp().run(configFile.toURI().toURL());
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
