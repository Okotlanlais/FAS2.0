package com.fas.fotomania.fotomania.services.implementations;

import com.fas.fotomania.fotomania.entities.Reservation;
import com.fas.fotomania.fotomania.entities.ReservationHours;
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

    @Autowired
    IReservationService reservationService;

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
                if ((endHour <= reservation.getStartHour() || startHour >= reservation.getEndHour())) {
                    flag = true;
                }
            }else{
                flag=true;
            }
        }
        return flag;
    }

    @Override
    public List<ReservationHours> hoursAndReservation(int companyId){
        List<ReservationHours> reservationHours= new ArrayList<>();
        for( int i=7;i<=21;i++){
            ReservationHours reservationHour= new ReservationHours();
            for (Reservation reservation:reservationService.findReservationsByCompany(companyId)) {
                if(i>=reservation.getStartHour()&&i<=reservation.getEndHour()&&!(reservation.isCompleted())){
                    if(reservation.getDay().equals("Monday")){
                        reservationHour.setMonday("Monday");
                    }
                    if(reservation.getDay().equals("Tuesday")){
                        reservationHour.setTuesday("Tuesday");
                    }
                    if(reservation.getDay().equals("Wednesday")){
                        reservationHour.setWednesday("Wednesday");
                    }
                    if(reservation.getDay().equals("Thursday")){
                        reservationHour.setThursday("Thursday");
                    }
                    if(reservation.getDay().equals("Friday")){
                        reservationHour.setFriday("Friday");
                    }
                    if(reservation.getDay().equals("Saturday")){
                        reservationHour.setSaturday("Saturday");
                    }
                    if(reservation.getDay().equals("Sunday")){
                        reservationHour.setSunday("Sunday");
                    }
                }
            }
            reservationHour.setHour(i);
            reservationHours.add(reservationHour);
            System.out.println(reservationHour.getHour());
            System.out.println(reservationHour.getFriday());
            System.out.println(reservationHour.getMonday());
            System.out.println(reservationHour.getSaturday());
            System.out.println(reservationHour.getSunday());
            System.out.println(reservationHour.getThursday());
            System.out.println(reservationHour.getTuesday());
            System.out.println(reservationHour.getWednesday());
        }
        return reservationHours;
    }
}
