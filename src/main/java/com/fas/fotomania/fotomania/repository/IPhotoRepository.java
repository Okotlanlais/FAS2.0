package com.fas.fotomania.fotomania.repository;

import com.fas.fotomania.fotomania.entities.Photo;
import com.fas.fotomania.fotomania.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPhotoRepository extends JpaRepository<Photo, Integer> {
    public List<Photo> findByUser(Optional<User> user);
}
