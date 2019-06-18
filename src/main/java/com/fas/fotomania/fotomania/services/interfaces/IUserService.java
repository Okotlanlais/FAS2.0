package com.fas.fotomania.fotomania.services.interfaces;

import com.fas.fotomania.fotomania.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    public void saveUser(User user);
    public boolean isUserAlreadyPresent(User user);
    public User findUserByEmail(String email);
}
