package com.apprentice.boundary.rest.profiles;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class TimeoutMinutesTestProfile implements QuarkusTestProfile {
    @Override
    public Map<String, String>  getConfigOverrides(){
        return Map.of(
            "TIMEOUT_MINUTES", "0"
        );
    }
}
