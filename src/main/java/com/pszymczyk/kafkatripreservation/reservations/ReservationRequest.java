package com.pszymczyk.kafkatripreservation.reservations;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReservationRequest(@JsonProperty("type") String type,
                                 @JsonProperty("tripCode") String tripCode,
                                 @JsonProperty("userId") String userId) {

}
