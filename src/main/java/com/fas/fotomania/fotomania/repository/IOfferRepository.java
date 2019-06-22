package com.fas.fotomania.fotomania.repository;

import com.fas.fotomania.fotomania.entities.Offer;
import com.fas.fotomania.fotomania.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOfferRepository extends JpaRepository<Offer, Integer> {
    public List<Offer> findByUser(Optional<User> user);
}
