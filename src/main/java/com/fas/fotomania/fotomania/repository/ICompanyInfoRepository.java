package com.fas.fotomania.fotomania.repository;

import com.fas.fotomania.fotomania.entities.CompanyInfo;
import com.fas.fotomania.fotomania.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICompanyInfoRepository extends JpaRepository<CompanyInfo, Integer> {
    public CompanyInfo findByUser(Optional<User> user);
}
