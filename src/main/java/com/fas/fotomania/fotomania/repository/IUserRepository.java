package com.fas.fotomania.fotomania.repository;

import com.fas.fotomania.fotomania.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
    public List<User> findByCompany(String company);
}
