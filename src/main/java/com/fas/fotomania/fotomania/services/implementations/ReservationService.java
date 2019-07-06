package com.fas.fotomania.fotomania.services.implementations;

import com.fas.fotomania.fotomania.entities.Reservation;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.repository.IReservationRepository;
import com.fas.fotomania.fotomania.repository.IUserRepository;
import com.fas.fotomania.fotomania.services.interfaces.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    IReservationRepository reservationRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    public boolean saveReservation(Reservation reservation) {
        boolean good=false;
        try {
            reservationRepository.save(reservation);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        boolean good=false;
        try {
            reservationRepository.save(reservation);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean deleteReservation(Reservation reservation) {
        boolean good=false;
        try {
            reservationRepository.delete(reservation);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public List<Reservation> listReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findReservationsByCompany(int userId) {
        Optional<User> currentUser=userRepository.findById(userId);
        return reservationRepository.findByCompany(currentUser);
    }

    @Override
    public List<Reservation> findReservationsByClient(int userId) {
        Optional<User> currentUser=userRepository.findById(userId);
        return reservationRepository.findByClient(currentUser);
    }

    @Override
    public Optional<Reservation> findById(int id) {
        return reservationRepository.findById(id);
    }
}
