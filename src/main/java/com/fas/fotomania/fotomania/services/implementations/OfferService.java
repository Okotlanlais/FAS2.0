package com.fas.fotomania.fotomania.services.implementations;

import com.fas.fotomania.fotomania.entities.Offer;
import com.fas.fotomania.fotomania.services.interfaces.IOfferService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService implements IOfferService {
    @Override
    public boolean saveOffer(Offer tool) {
        return false;
    }

    @Override
    public boolean updateOffer(Offer tool) {
        return false;
    }

    @Override
    public boolean deleteOffer(Offer tool) {
        return false;
    }

    @Override
    public List<Offer> listOffers() {
        return null;
    }

    @Override
    public List<Offer> findOffersByCompany(int companyId) {
        return null;
    }

    @Override
    public Optional<Offer> findById(int id) {
        return Optional.empty();
    }
}
