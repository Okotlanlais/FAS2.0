package com.fas.fotomania.fotomania.repository;

import com.fas.fotomania.fotomania.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer> {

}
