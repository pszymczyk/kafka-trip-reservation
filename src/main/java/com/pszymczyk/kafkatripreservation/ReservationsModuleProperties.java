package com.pszymczyk.kafkatripreservation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "reservations")
public class ReservationsModuleProperties {
    String reservationsRequestsTopic;

    public String getReservationsRequestsTopic() {
        return reservationsRequestsTopic;
    }

    public void setReservationsRequestsTopic(String reservationsRequestsTopic) {
        this.reservationsRequestsTopic = reservationsRequestsTopic;
    }
}
