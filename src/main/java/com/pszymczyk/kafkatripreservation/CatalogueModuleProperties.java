package com.pszymczyk.kafkatripreservation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "catalogue")
public class CatalogueModuleProperties {

    private String tripEventsTopic;

    public String getTripEventsTopic() {
        return tripEventsTopic;
    }

    public void setTripEventsTopic(String tripEventsTopic) {
        this.tripEventsTopic = tripEventsTopic;
    }
}
