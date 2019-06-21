package com.fas.fotomania.fotomania.services.interfaces;

import com.fas.fotomania.fotomania.entities.Specialty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ISpecialtyService {
    public boolean saveSpecialty(Specialty specialty);
    public boolean updateSpecialty(Specialty specialty);
    public boolean deleteSpecialty(Specialty specialty);
    public List<Specialty> listSpecialties();
    public List<Specialty> findSpecialtyByCompany(int companyId);
    public Optional<Specialty> findById(int id);

}
