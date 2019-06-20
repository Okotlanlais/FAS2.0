package com.fas.fotomania.fotomania.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fas.fotomania.fotomania.entities.Tool;
import com.fas.fotomania.fotomania.entities.User;

@Repository
public interface IToolRepository extends JpaRepository<Tool, Integer>{
	public List<Tool> findByUser(Optional<User> user);
}
