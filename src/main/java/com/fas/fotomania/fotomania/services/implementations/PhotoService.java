package com.fas.fotomania.fotomania.services.implementations;

import com.fas.fotomania.fotomania.entities.Photo;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.repository.IPhotoRepository;
import com.fas.fotomania.fotomania.repository.IUserRepository;
import com.fas.fotomania.fotomania.services.interfaces.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService implements IPhotoService {

    @Autowired
    IPhotoRepository photoRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    public boolean savePhoto(Photo photo) {
        boolean good=false;
        try {
            photoRepository.save(photo);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean updatePhoto(Photo photo) {
        boolean good=false;
        try {
            photoRepository.save(photo);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean deletePhoto(Photo photo) {
        boolean good=false;
        try {
            photoRepository.delete(photo);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public List<Photo> listPhotos() {
        return photoRepository.findAll();
    }

    @Override
    public List<Photo> findPhotosByCompany(int companyId) {
        Optional<User> currentCompany=userRepository.findById(companyId);
        return photoRepository.findByUser(currentCompany);
    }

    @Override
    public Optional<Photo> findById(int id) {
        return photoRepository.findById(id);
    }
}
