/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.ood.examples.cardvalidator.cardservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solent.ac.uk.ood.examples.cardvalidator.model.Account;
import solent.ac.uk.ood.examples.cardvalidator.model.AccountDAO;
import solent.ac.uk.ood.examples.cardvalidator.model.BankApi;
import solent.ac.uk.ood.examples.cardvalidator.model.CardFactoryDAO;
import solent.ac.uk.ood.examples.cardvalidator.model.CreditCard;
import solent.ac.uk.ood.examples.cardvalidator.model.CreditCardFactoryAndValidator;

/**
 *
 * @author cgallen
 */
public class BankApiImpl implements BankApi {

    private static final Logger LOG = LoggerFactory.getLogger(BankApiImpl.class);
    
    private static final Logger TRANSACTIONLOG = LoggerFactory.getLogger("transaction-log");

    private final CardFactoryDAO cardFactoryDao;

    private final AccountDAO accountDAO;

    public BankApiImpl(CardFactoryDAO cardFactoryDao, AccountDAO accountDAO) {
        this.cardFactoryDao = cardFactoryDao;
        this.accountDAO = accountDAO;
    }

    @Override
    public Account createAccount(String issuerIdentificationNumber, String name) {
        return accountDAO.createAccount(issuerIdentificationNumber, name);
    }

    @Override
    public boolean deleteAccount(String issuerIdentificationNumber, String individualAccountIdentifier) {
        return accountDAO.deleteAccount(issuerIdentificationNumber, individualAccountIdentifier); 
    }

    @Override
    public Account retrieveAccount(String issuerIdentificationNumber, String individualAccountIdentifier) {
        return accountDAO.retrieveAccount(issuerIdentificationNumber, individualAccountIdentifier); 
    }

    @Override
    public Account updateAccount(Account account) {
        return accountDAO.updateAccount(account);
    }

    @Override
    public List<Account> getAccountsForIssuer(String issuerIdentificationNumber) {
        return accountDAO.getAccountsForIssuer(issuerIdentificationNumber); 
    }

    @Override
    public List<String> getSupportedIssuerNames() {
        return cardFactoryDao.getSupportedIssuerNames(); 
    }

    @Override
    public String getIssuerIdentifierNumberForName(String name) {
        return cardFactoryDao.getIssuerIdentifierNumberForName(name); 
    }

    @Override
    public String getNameForIssuerIdentificationNumber(String issuerIdentificationNumber) {
        return cardFactoryDao.getNameForIssuerIdentificationNumber(issuerIdentificationNumber); 
    }

    @Override
    public CreditCard createNewCreditCard(Account account) {
        CreditCardFactoryAndValidator creditcardfactory = cardFactoryDao.getCreditCardFactoryAndValidator(account.getIssuerIdentificationNumber());
        Date currentDate = new Date();
        long current = currentDate.getTime();
        long end = current + 1000L * 60L * 60L * 24L * 30L;
        Date endDate = new Date(end);
        String pattern = "MMYY";
        SimpleDateFormat dateFromat = new SimpleDateFormat(pattern);
        String endDateStr = dateFromat.format(endDate);
    
        String issue = account.getCurrentCardIssueNumber().toString();
        CreditCard creditcard = creditcardfactory.createCreditCard(account.getIndividualAccountIdentifier(), account.getName(), endDateStr, issue);
        return creditcard;
    }

}
