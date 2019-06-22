package com.fas.fotomania.fotomania.services.implementations;

import com.fas.fotomania.fotomania.entities.Offer;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.repository.IOfferRepository;
import com.fas.fotomania.fotomania.repository.IUserRepository;
import com.fas.fotomania.fotomania.services.interfaces.IOfferService;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService implements IOfferService {

    @Autowired
    IOfferRepository offerRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    public boolean saveOffer(Offer offer) {
        boolean good=false;
        try {
            offerRepository.save(offer);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean updateOffer(Offer offer) {
        boolean good=false;
        try {
            offerRepository.save(offer);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean deleteOffer(Offer offer) {
        boolean good=false;
        try {
            offerRepository.delete(offer);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public List<Offer> listOffers() {
        return offerRepository.findAll();
    }

    @Override
    public List<Offer> findOffersByCompany(int companyId) {
        Optional<User> currentCompany=userRepository.findById(companyId);
        return offerRepository.findByUser(currentCompany);
    }

    @Override
    public Optional<Offer> findById(int id) {
        return offerRepository.findById(id);
    }
}
