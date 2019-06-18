package com.fas.fotomania.fotomania.repository;

import com.fas.fotomania.fotomania.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Integer> {
    public Role findByName(String name);
}
