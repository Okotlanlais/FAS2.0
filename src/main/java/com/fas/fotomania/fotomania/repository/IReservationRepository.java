package com.fas.fotomania.fotomania.repository;

import com.fas.fotomania.fotomania.entities.Reservation;
import com.fas.fotomania.fotomania.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer> {
    public List<Reservation> findByClient(Optional<User> user);
    public List<Reservation> findByCompany(Optional<User> user);
}
