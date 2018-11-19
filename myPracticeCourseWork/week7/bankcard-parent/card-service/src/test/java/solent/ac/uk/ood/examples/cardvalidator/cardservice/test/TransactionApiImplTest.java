/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.ood.examples.cardvalidator.cardservice.test;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solent.ac.uk.ood.examples.cardvalidator.accountdao.jaxbimpl.AccountDAOJaxbImpl;
import solent.ac.uk.ood.examples.cardvalidator.cardservice.TransactionApiImpl;
import solent.ac.uk.ood.examples.cardvalidator.cvv.impl.TrivialCvvAlgorythimStrategy;
import solent.ac.uk.ood.examples.cardvalidator.impl.CardFactoryDAOImpl;
import solent.ac.uk.ood.examples.cardvalidator.impl.CreditCardFactoryAndValidatorImpl;
import solent.ac.uk.ood.examples.cardvalidator.impl.SupportedCardIssuerIdentificationNumbers;
import solent.ac.uk.ood.examples.cardvalidator.model.Account;
import solent.ac.uk.ood.examples.cardvalidator.model.AccountDAO;
import solent.ac.uk.ood.examples.cardvalidator.model.CardFactoryDAO;
import solent.ac.uk.ood.examples.cardvalidator.model.CreditCard;
import solent.ac.uk.ood.examples.cardvalidator.model.CreditCardFactoryAndValidator;
import solent.ac.uk.ood.examples.cardvalidator.model.CvvAlgorythimStrategy;
import solent.ac.uk.ood.examples.cardvalidator.model.Transaction;
import solent.ac.uk.ood.examples.cardvalidator.model.TransactionApi;

/**
 *
 * @author cgallen
 */
public class TransactionApiImplTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(AccountDAOJaxbImplTest.class);

    public final String TEST_DATA_FILE_LOCATION = "target/testAccountDaoData.xml";
   
    @Test
    public void testTransactionApi() {
       
    
         // delete test file at start of test
        File file = new File(TEST_DATA_FILE_LOCATION);
        file.delete();
        assertFalse(file.exists());
        
        AccountDAO accountDAO = new AccountDAOJaxbImpl(TEST_DATA_FILE_LOCATION);

        assertTrue(file.exists());

        // create new account1 provider 1
        String providerIIN1 = SupportedCardIssuerIdentificationNumbers.VISA_BANK_OF_IRELAND_UK;
        String name1 = "Ellie Morgan";
        Account account1 = accountDAO.createAccount(providerIIN1, name1);
        assertNotNull(account1);
        LOG.debug("created account 1 : " + account1);

        // create new account2 provider 2
        String providerIIN2 = SupportedCardIssuerIdentificationNumbers.MASTERCARD_LLOYDS_BANK_PLC;
        String name2 = "Body Shop";
        Account account2 = accountDAO.createAccount(providerIIN2, name2);
        assertNotNull(account2);
        LOG.debug("created account 2 : " + account2);
        
        
        CardFactoryDAO cardFactoryDao = new CardFactoryDAOImpl();
        
        TransactionApi  transactionApi = new TransactionApiImpl(cardFactoryDao, accountDAO);
        
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(Double.NaN);
        
//        CreditCardFactoryAndValidator ccFactoryandValidator = new CreditCardFactoryAndValidatorImpl();
//
//        // set trivial algorythm
//        CvvAlgorythimStrategy cvvAlgorythimStrategy = new TrivialCvvAlgorythimStrategy();
//        ccFactoryandValidator.setCvvAlgorythim(cvvAlgorythimStrategy);
//
//        // set provider
//        String issuerIdentificationNumber = SupportedCardIssuerIdentificationNumbers.VISA_BANK_OF_IRELAND_UK;
//        ccFactoryandValidator.setIssuerIdentificationNumber(issuerIdentificationNumber);
//
//        String accountNumber = "123456789"; // 9 digits + iin + 1 = 16
//        String name = "Ellie Morgan";
//        String endDate = "0119";
//        String issueNumber = "01";
//        CreditCard newCreditcard = ccFactoryandValidator.createCreditCard(accountNumber, name, endDate, issueNumber);
//
//        System.out.println(" new creditcard created:" + newCreditcard);
//
//        boolean cvvIsValid = ccFactoryandValidator.cvvIsValid(newCreditcard);
//        assertTrue(cvvIsValid);
//
//        boolean lunnIsValid = ccFactoryandValidator.cardNumberLunnIsValid(newCreditcard);
//        assertTrue(lunnIsValid);
//        
//        String shopIssuerIdentificationNumber = SupportedCardIssuerIdentificationNumbers.MASTERCARD_LLOYDS_BANK_PLC;
//        ccFactoryandValidator.setIssuerIdentificationNumber(issuerIdentificationNumber);
//
//        String shopAccountNumber = "123456790"; // 9 digits + iin + 1 = 16
//        String shopName = "Body Shop";
//        String shopEndDate = "0120";
//        String shopIssueNumber = "01";
//        CreditCard shopCreditcard = ccFactoryandValidator.createCreditCard(shopAccountNumber, shopName, shopEndDate, shopIssueNumber);
//
//        System.out.println(" new creditcard created:" + newCreditcard);
        
    }
}
