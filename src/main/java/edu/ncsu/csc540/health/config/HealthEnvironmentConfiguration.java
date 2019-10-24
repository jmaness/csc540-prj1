package edu.ncsu.csc540.health.config;

import org.apache.commons.configuration2.MapConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HealthEnvironmentConfiguration extends MapConfiguration {

    private static final String PREFIX = "HEALTH_";

    /**
     * Configuration that uses environment variables following these
     * conventions:
     *
     * 1. All uppercase
     * 2. Start with the prefix "HEALTH_
     * 3. All .'s in the application configuration property name
     * should be replace with underscores in the environment variable name
     *
     * For example, the environment variable "HEALTH_DB_USER" will be
     * transformed to the property "db.user".
     */
    public HealthEnvironmentConfiguration() {
        super(new HashMap<String, Object>(System.getenv()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(PREFIX))
                .collect(Collectors.toMap(
                    entry -> entry.getKey()
                            .replaceFirst(PREFIX, "")
                            .replace("_", ".")
                            .toLowerCase(),
                    Map.Entry::getValue
                ))
        ));
    }
}
