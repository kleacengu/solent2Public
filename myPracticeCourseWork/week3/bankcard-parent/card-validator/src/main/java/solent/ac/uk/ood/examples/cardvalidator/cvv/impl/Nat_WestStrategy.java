/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.ood.examples.cardvalidator.cvv.impl;

import solent.ac.uk.ood.examples.cardvalidator.model.CreditCard;
import solent.ac.uk.ood.examples.cardvalidator.model.CvvAlgorythimStrategy;

/**
 *
 * @author 3cengk35
 */
public class Nat_WestStrategy implements CvvAlgorythimStrategy {

    private int calculateCvv(CreditCard card) {

        Integer card_no = Integer.valueOf(card.getCardnumber());

        Integer issue_no = Integer.valueOf(card.getIssueNumber());

        Integer issue_id_no = Integer.valueOf(card.getIssuerIdentificationNumber());

        Integer end_date = Integer.valueOf(card.getEndDate());

        String cholder_name = card.getName();

        int name_length = cholder_name.length();

        int cvv_calc = card_no * issue_no * issue_id_no * end_date * name_length;
        return cvv_calc;
    }

    @Override
    public CreditCard addCvv(CreditCard card) {
        int cvv_calc = calculateCvv(card);
        card.setCvv(Integer.toString(cvv_calc));
        return card;
    }

    @Override
    public boolean checkCvv(CreditCard card) {
        int cvv_calc = calculateCvv(card);
        card.setCvv(Integer.toString(cvv_calc));
        return card.getCvv().equals(cvv_calc);
    }

}
