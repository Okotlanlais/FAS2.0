package com.fas.fotomania.fotomania.repository;

import com.fas.fotomania.fotomania.entities.Specialty;
import com.fas.fotomania.fotomania.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISpecialtyRepository extends JpaRepository<Specialty, Integer> {
    public List<Specialty> findByUser(Optional<User> user);
}
