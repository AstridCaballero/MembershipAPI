package com.apprentice.boundary.rest.profiles;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

/**
 * This Test Profile is used to override configuration variables for testing purposes
 * such as the TIMEOUT_MINUTES which is 3 in the application.properties file
 * but for testing timeout TIMEOUT_MINUTES needs to be Zero.
 */
public class TimeoutMinutesTestProfile implements QuarkusTestProfile {
    @Override
    public Map<String, String>  getConfigOverrides(){
        return Map.of(
            "TIMEOUT_MINUTES", "0"
        );
    }
}
