package com.pszymczyk.kafkatripreservation.step3;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TripRegisteredEvent(
        @JsonProperty("type") String type,
        @JsonProperty("tripCode") String tripCode,
        @JsonProperty("seatsNumber") int seatsNumber) {

}
