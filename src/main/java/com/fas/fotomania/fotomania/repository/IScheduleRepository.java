package com.fas.fotomania.fotomania.repository;

import com.fas.fotomania.fotomania.entities.Schedule;
import com.fas.fotomania.fotomania.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IScheduleRepository extends JpaRepository<Schedule, Integer> {
    public Schedule findByUser(Optional<User> user);
}
