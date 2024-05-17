package com.pszymczyk.kafkatripreservation.reservations;

import jakarta.persistence.*;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toMap;

@Entity(name = "ReservationsTripEntity")
public class TripEntity {

    @Id
    @GeneratedValue
    private long entityId;

    private String tripCode;

    private int seatsNumber;

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="TRIP_ID")
    private Set<ReservationEntity> reservations;

    @ElementCollection
    private Set<String> reservationRequestsIds;

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public Set<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public void update(Trip trip) {
        updateReservations(trip);
        updateReservationsRequestId(trip);

    }

    private void updateReservations(Trip trip) {
        Map<UUID, Reservation> grouped = trip.getReservations()
                                             .stream()
                                             .collect(toMap(Reservation::getId, x -> x));

        for (ReservationEntity reservationEntity: reservations) {
            Reservation reservation = grouped.get(reservationEntity.getId());
            reservationEntity.setStatus(reservation.getStatus().name());
            grouped.remove(reservation.getId());
        }

        for (Reservation reservation: grouped.values()) {
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setId(reservation.getId());
            reservationEntity.setUserId(reservation.getUserId());
            reservationEntity.setStatus(reservation.getStatus().name());
            reservations.add(reservationEntity);
        }
    }

    private void updateReservationsRequestId(Trip trip) {
        reservationRequestsIds.addAll(trip.getReservationRequestsIds());
    }

    public Set<String> getReservationRequestsIds() {
        return reservationRequestsIds;
    }

    public void setReservationRequestsIds(Set<String> reservationRequestsIds) {
        this.reservationRequestsIds = reservationRequestsIds;
    }
}
