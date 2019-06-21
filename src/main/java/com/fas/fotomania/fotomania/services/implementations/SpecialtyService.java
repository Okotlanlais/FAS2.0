package com.fas.fotomania.fotomania.services.implementations;

import com.fas.fotomania.fotomania.entities.Specialty;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.repository.ISpecialtyRepository;
import com.fas.fotomania.fotomania.repository.IUserRepository;
import com.fas.fotomania.fotomania.services.interfaces.ISpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyService implements ISpecialtyService {

    @Autowired
    ISpecialtyRepository specialtyRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    public boolean saveSpecialty(Specialty specialty) {
        boolean good=false;
        try {
            specialtyRepository.save(specialty);
            good=true;
        } catch (Exception e){
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean updateSpecialty(Specialty specialty) {
        boolean good=false;
        try {
            specialtyRepository.save(specialty);
            good=true;
        } catch (Exception e){
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean deleteSpecialty(Specialty specialty) {
        boolean good=false;
        try {
            specialtyRepository.delete(specialty);
            good=true;
        } catch (Exception e){
            System.out.println(e);
        }
        return good;
    }

    @Override
    public List<Specialty> listSpecialties() {
        return specialtyRepository.findAll();
    }

    @Override
    public List<Specialty> findSpecialtyByCompany(int companyId) {
        Optional<User> currentUser= userRepository.findById(companyId);
        return specialtyRepository.findByUser(currentUser);
    }

    @Override
    public Optional<Specialty> findById(int id) {
        return specialtyRepository.findById(id);
    }
}
