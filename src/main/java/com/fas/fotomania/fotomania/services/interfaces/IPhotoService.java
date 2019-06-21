package com.fas.fotomania.fotomania.services.interfaces;

import com.fas.fotomania.fotomania.entities.Photo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IPhotoService {
    public boolean savePhoto(Photo photo);
    public boolean updatePhoto(Photo photo);
    public boolean deletePhoto(Photo photo);
    public List<Photo> listPhotos();
    public List<Photo> findPhotosByCompany(int companyId);
    public Optional<Photo> findById(int id);
}
