package com.vegapay.demo.dao;

import com.vegapay.demo.domain.Offer;
import com.vegapay.demo.repository.AccountRepository;
import com.vegapay.demo.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class OfferDAO {
    @Autowired
    OfferRepository offerRepository;

    @Autowired
    AccountRepository accountRepository;

    public List<Offer> getOffers() {
        return offerRepository.findAll();
    }

    public void saveOffer(Offer offer){
        offerRepository.save(offer);
    }

    public Offer getById(String id){
        return offerRepository.getById(id);
    }

    public void updateOfferStatus(String id, String status){
         offerRepository.updateOfferStatus(id, status);
    }
    public List<Offer> getAllActiveOfferByAccountIdAndDate(String accountId, Timestamp date){
       return offerRepository.getAllActiveOfferByAccountIdAndDate(accountId, date);
    }

}
