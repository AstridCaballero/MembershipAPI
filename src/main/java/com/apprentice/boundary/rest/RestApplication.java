package com.apprentice.boundary.rest;

import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Top level entry point for the REST services.
 */

@LoginConfig(authMethod = "MP-JWT", realmName = "apprentice")
@ApplicationPath("/api")
@OpenAPIDefinition(info = @Info(title = "Membership API REST application", version = "1.0.0"), components =
@Components(securitySchemes = {
        @SecurityScheme(securitySchemeName = "JWT", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)}))
public class RestApplication extends Application {
}
