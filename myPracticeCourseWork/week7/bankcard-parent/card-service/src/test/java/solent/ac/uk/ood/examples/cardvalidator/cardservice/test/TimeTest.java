/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.ood.examples.cardvalidator.cardservice.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 3cengk35
 */
public class TimeTest {

    @Test
    public void timeTest() {

        Date currentDate = new Date();
        Long current = currentDate.getTime();
        System.out.println(current);
        Long end = current + 1000L * 60L * 60L * 24L * 30L;
        System.out.println(1000L * 60L * 60L * 24L * 30L);
        Date endDate = new Date(end);
        String pattern = "MMYYYY";
        SimpleDateFormat dateFromat = new SimpleDateFormat(pattern);
        System.out.println(currentDate);
        System.out.println(dateFromat.format(currentDate));
        System.out.println(endDate);
        System.out.println(dateFromat.format(endDate));
    }

}
