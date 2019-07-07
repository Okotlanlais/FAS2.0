package com.fas.fotomania.fotomania.services.interfaces;

import com.fas.fotomania.fotomania.entities.Reservation;
import com.fas.fotomania.fotomania.entities.ReservationHours;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IReservationService {
    public boolean saveReservation(Reservation reservation);
    public boolean updateReservation(Reservation reservation);
    public boolean deleteReservation(Reservation reservation);
    public List<Reservation> listReservations();
    public List<Reservation> findReservationsByCompany(int userId);
    public List<Reservation> findReservationsByClient(int userId);
    public Optional<Reservation> findById(int id);
    public String generateRandomCode(int length);
    public boolean checkAvailability(int companyId, int startHour, int endHour, String day);
    public List<ReservationHours> hoursAndReservation(int companyId);
}
