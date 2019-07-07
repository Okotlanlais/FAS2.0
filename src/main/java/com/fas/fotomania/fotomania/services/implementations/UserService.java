package com.fas.fotomania.fotomania.services.implementations;

import com.fas.fotomania.fotomania.entities.Role;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.repository.IRoleRepository;
import com.fas.fotomania.fotomania.repository.IUserRepository;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setStatus("VERIFIED");
        Role userRole;
        //System.out.println(user.getCompany());
        if(user.getCompany().equals("true")){
            userRole = roleRepository.findByName("COMPANY_USER");
            //System.out.println("Guardando como Empresa");
        }else {
            user.setCompany("false");
            userRole = roleRepository.findByName("CLIENT_USER");
            //System.out.println("Guardando como Usuario");
        }

        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public boolean isUserAlreadyPresent(User user) {
        return false;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllCompanies() {
        return userRepository.findByCompany("true");
    }

    @Override
    public Optional<User> findCompanyById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findByName(String name) {
        List<User> companies = new ArrayList<>();
        for (User company:userRepository.findByName(name)) {
           if(company.getCompany().equals("true")){
               companies.add(company);
           }
        }
        return companies;
    }
}
