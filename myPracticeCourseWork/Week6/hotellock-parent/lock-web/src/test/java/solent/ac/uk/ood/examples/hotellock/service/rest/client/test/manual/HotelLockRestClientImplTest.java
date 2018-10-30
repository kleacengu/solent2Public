/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.ood.examples.hotellock.service.rest.client.test.manual;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import static org.junit.Assert.*;
import solent.ac.uk.ood.examples.hotellock.model.CardKey;
import solent.ac.uk.ood.examples.hotellock.service.rest.client.HotelLockRestClientImpl;
import solent.ac.uk.ood.examples.hotellock.service.rest.client.HotelReceptionRestClientImpl;

/**
 *
 * @author cgallen
 */
public class HotelLockRestClientImplTest {

    String baseUrl = "http://localhost:8680";
    MediaType mediaType = MediaType.APPLICATION_XML_TYPE;

    @Test
    public void testUnlock() {

        HotelLockRestClientImpl client = new HotelLockRestClientImpl(baseUrl, mediaType);
        String roomNumber = "200";
        String cardCode = "3230302C31363663343862356538302C31363663396231626138302C313A6134386535356130";

        boolean generatedDoorLock = client.unlockDoor(cardCode, roomNumber);

        System.out.println("generatedCardCode:" + generatedDoorLock);

        assertTrue(generatedDoorLock);
        
       // assertEquals(requestStartDateStr, receivedStartDateStr);
       // assertEquals(requestEndDateStr, receivedEndDateStr);

    }

}
