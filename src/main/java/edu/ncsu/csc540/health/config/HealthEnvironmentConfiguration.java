package edu.ncsu.csc540.health.config;

import org.apache.commons.configuration2.MapConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HealthEnvironmentConfiguration extends MapConfiguration {

    private static final String PREFIX = "HEALTH_";

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
