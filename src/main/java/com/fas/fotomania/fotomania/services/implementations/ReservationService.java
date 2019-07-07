package com.fas.fotomania.fotomania.services.implementations;

import com.fas.fotomania.fotomania.entities.Reservation;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.repository.IReservationRepository;
import com.fas.fotomania.fotomania.repository.IUserRepository;
import com.fas.fotomania.fotomania.services.interfaces.IReservationService;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    IReservationRepository reservationRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IUserService userService;

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

    @Override
    public String generateRandomCode(int length) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            int index = (int)(AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    @Override
    public boolean checkAvailability(int companyId, int startHour, int endHour, String day) {
        boolean flag=false;
        for (Reservation reservation:findReservationsByCompany(companyId)) {
            if(day.equals(reservation.getDay())) {
                System.out.println("mismo dia");
                System.out.println("EndHourNueva: "+endHour);
                System.out.println("StartHourVieja: "+reservation.getStartHour());
                System.out.println("StartHourNueva: "+startHour);
                System.out.println("EndHourVieja: "+reservation.getEndHour());
                if ((endHour <= reservation.getStartHour() || startHour >= reservation.getEndHour())) {
                    System.out.println("horas libres");
                    flag = true;
                }
            }else{System.out.println("No mismo dia");}
        }
        return flag;
    }
}
