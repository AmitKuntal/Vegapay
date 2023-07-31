package com.vegapay.demo.services;

import com.vegapay.demo.dao.AccountDAO;
import com.vegapay.demo.dao.OfferDAO;
import com.vegapay.demo.domain.Account;
import com.vegapay.demo.domain.Offer;
import com.vegapay.demo.dto.CreateOfferDto;
import com.vegapay.demo.dto.GenericResponse;
import com.vegapay.demo.dto.UpdateOfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OfferHelperService{

    @Autowired
    private OfferDAO offerDAO;

    @Autowired
    AccountDAO accountDAO;



    public GenericResponse createOffer(CreateOfferDto body) throws Exception {
        if(body.getAccountId()==null){
            throw new Exception("Account Id can't not be null");
        }else if (body.getLimitType() == null){
            throw new Exception("Limit type can't not be null");
        }else{
            return checkOfferTimings(body);
        }
    }

    private GenericResponse checkOfferTimings(CreateOfferDto body) throws Exception {
        if(body.getOfferActivationTime().before(getCurrentTimeStamp()) && body.getOfferActivationTime().after(body.getOfferExpiryTime())){
            throw  new Exception("Offer Activation date and Offer Expiry date should be in future");

        }else{
            return checkAccountExist(body);
        }
    }

    private GenericResponse checkAccountExist(CreateOfferDto body) throws Exception {
        Account account = accountDAO.getAccountByAccountId(body.getAccountId());
        if(account ==null){
            throw new Exception("Account not found");
        }else{
            return checkOfferLimit(account, body);
        }
    }

    private GenericResponse checkOfferLimit(Account account, CreateOfferDto body) throws Exception {
        Long currentLimitValue = getCurrentLimitValueFromAccount(account, body.getLimitType());
        if(currentLimitValue >= body.getLimit()){
            throw new Exception("Offer limit can't be less then current offer limit");
        }
        else{
            Offer offer =  new Offer();
            offer.setLimitType(body.getLimitType());
            offer.setLimitValue(body.getLimit());
            offer.setOfferActivationTime(body.getOfferActivationTime());
            offer.setOfferExpiryTime(body.getOfferExpiryTime());
            offer.setStatus("PENDING");
            offer.setAccountId(body.getAccountId());
            offer.setId(getUUID());

            offerDAO.saveOffer(offer);
            GenericResponse response = new GenericResponse();
            response.setMessage("Offer Created");
            response.setStatus("success");
            return response;
        }
    }

    private Long getCurrentLimitValueFromAccount(Account account, String limitType){
        if(limitType.equalsIgnoreCase("ACCOUNT_LIMIT")){
            return account.getAccountLimit();
        }else{
            return account.getPerTransactionLimit();
        }
    }


    private Timestamp getCurrentTimeStamp(){
        java.util.Date currentDate =  new java.util.Date();
        return new Timestamp(currentDate.getTime());
    }

    private String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }


    public List<Offer> getActiveOffer(String accountId, String date){
        Timestamp dateTimestamp = getTimeStampFromStringDate(date);
        return offerDAO.getAllActiveOfferByAccountIdAndDate(accountId, dateTimestamp);
    }

    public GenericResponse updateOffer(UpdateOfferDto body) throws Exception {
        if(body.getLimitOfferId() == null){
            throw new Exception("Limit offer id can't be null");
        } else if (body.getStatus() == null) {
            throw new Exception("Status can't be null");
        }else{
            return updateOfferStatus(body);
        }
    }

    private GenericResponse updateOfferStatus(UpdateOfferDto body) throws Exception {
        String status = body.getStatus().toUpperCase();
        GenericResponse response = null;
        switch (status){
            case "ACCEPT":
                response = acceptOffer(body.getLimitOfferId());
                break;
            case "REJECT":
                response = rejectOffer(body.getLimitOfferId());
                break;
            default:
                throw new Exception("Offer can be accepted or rejected");
        }
        return response;
    }

    private GenericResponse acceptOffer(String offerId) throws Exception {
        Offer offer = offerDAO.getById(offerId);
        if(offer == null) throw new Exception("Offer not found");
        offer.setStatus("ACCEPTED");
        System.out.println("offer.accountId" + offer.getAccountId());
        Account account = accountDAO.getAccountByAccountId(offer.getAccountId());
        if(offer.getLimitType().equalsIgnoreCase("ACCOUNT_LIMIT")){
            account.setLastAccountLimit(account.getAccountLimit());
            account.setAccountLimit(offer.getLimitValue());
            account.setAccountLimitUpdateTime(getCurrentTimeStamp());
        }
        else if(offer.getLimitType().equalsIgnoreCase("PER_TRANSACTION_LIMIT")){
            account.setLastPerTransactionLimit(account.getPerTransactionLimit());
            account.setPerTransactionLimit(offer.getLimitValue());
            account.setPerTransactionLimitUpdateTime(getCurrentTimeStamp());
        }

        offerDAO.saveOffer(offer);
        accountDAO.createAccount(account);

        GenericResponse response = new GenericResponse();
        response.setStatus("success");
        response.setMessage("Offer Accepted successfully ");

        return response;
    }

    private GenericResponse rejectOffer(String offerId){
        offerDAO.updateOfferStatus(offerId, "REJECTED");
        GenericResponse response = new GenericResponse();

        response.setStatus("success");
        response.setMessage("Offer Rejected successfully ");

        return response;
    }

    private Timestamp getTimeStampFromStringDate(String date){
        return new Timestamp(parseStringToDate(date).getTime());
    }

    private Date parseStringToDate(String date){

        try{
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        }catch (Exception exception){
            return new Date();
        }

    }


}