/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.ood.examples.hotellock.reception.test;


import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import solent.ac.uk.ood.examples.hotellock.model.HotelReceptionService;
import solent.ac.uk.ood.examples.hotellock.model.SecretKeyProvider;
import solent.ac.uk.ood.examples.hotellock.reception.HotelReceptionServiceImpl;
import solent.ac.uk.ood.examples.hotellock.secretkey.SecretKeyProviderImpl;

/**
 *
 * @author 3cengk35
 */
public class HotelReceptionServiceTest {
    
    @Test
    public void testTransactionLog() {
      
        HotelReceptionService hotelReceptionService  = new HotelReceptionServiceImpl();
        SecretKeyProvider secretKeyProvider = new SecretKeyProviderImpl();;
        hotelReceptionService.setSecretKeyProvider(secretKeyProvider);
        String roomNumber = "JM310";
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 1000 * 60 * 60 * 24); // 1 day later
        hotelReceptionService.createCardCode(roomNumber, startDate, endDate);
        System.out.println("Start date = " + startDate);
        System.out.println("End date = " + endDate);
    }
    
}
