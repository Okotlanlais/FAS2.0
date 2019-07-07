package com.fas.fotomania.fotomania.services.interfaces;

import com.fas.fotomania.fotomania.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {
    public void saveUser(User user);
    public boolean isUserAlreadyPresent(User user);
    public User findUserByEmail(String email);
    public List<User> findAllCompanies();
    public Optional<User> findCompanyById(int id);
    public List<User> findByName(String name);
}
