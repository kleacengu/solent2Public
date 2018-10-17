/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.ood.examples.hotellock.reception;

import java.util.Date;
import solent.ac.uk.ood.examples.hotellock.model.CardKey;
import solent.ac.uk.ood.examples.hotellock.model.HotelReceptionService;
import solent.ac.uk.ood.examples.hotellock.model.SecretKeyProvider;
import solent.ac.uk.ood.examples.hotellock.secretkey.SecretKeyProviderImpl;

/**
 *
 * @author cgallen
 */
public class HotelReceptionServiceImpl implements HotelReceptionService {

    private SecretKeyProvider secretKeyProvider;
    
    @Override
    public String createCardCode(String roomNumber, Date startDate, Date endDate) {
        
        //String roomNumber = "100a";
        int issueNumber = 01;
       // Date startDate = new Date();
        //Date endDate = new Date(startDate.getTime() + 1000 * 60 * 60 * 24); // 1 day later

        CardKey cardKey = new CardKey();
        cardKey.setRoomNumber(roomNumber);
        cardKey.setIssueNumber(issueNumber);
        cardKey.setStartDate(startDate);
        cardKey.setEndDate(endDate);
        System.out.println(cardKey);

       
        String cardString = secretKeyProvider.encodeCard(cardKey);
        return cardString;
    }

    @Override
    public CardKey readCard(String cardString) {
        CardKey decodedCardKey = secretKeyProvider.decodeCard(cardString);
        return decodedCardKey;
    }

    @Override
    public void setSecretKeyProvider(SecretKeyProvider secretKeyProvider) {
        this.secretKeyProvider= secretKeyProvider;
    }
    
}
