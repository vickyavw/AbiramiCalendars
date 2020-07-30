package com.abirami.api;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author vicky
 *
 */
@ApplicationPath ("/api")
public class WebService extends ResourceConfig {
    public WebService() {
        packages("com.abirami.api");
    }
}