package com.fas.fotomania.fotomania.services.interfaces;

import com.fas.fotomania.fotomania.entities.Offer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IOfferService {
    public boolean saveOffer(Offer offer);
    public boolean updateOffer(Offer offer);
    public boolean deleteOffer(Offer offer);
    public List<Offer> listOffers();
    public List<Offer> findOffersByCompany(int companyId);
    public Optional<Offer> findById(int id);
}
