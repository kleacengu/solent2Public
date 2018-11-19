/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.ood.examples.cardvalidator.cardservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solent.ac.uk.ood.examples.cardvalidator.model.Account;
import solent.ac.uk.ood.examples.cardvalidator.model.AccountDAO;
import solent.ac.uk.ood.examples.cardvalidator.model.CardFactoryDAO;
import solent.ac.uk.ood.examples.cardvalidator.model.CreditCard;
import solent.ac.uk.ood.examples.cardvalidator.model.CreditCardFactoryAndValidator;
import solent.ac.uk.ood.examples.cardvalidator.model.ResultCode;
import solent.ac.uk.ood.examples.cardvalidator.model.Transaction;
import solent.ac.uk.ood.examples.cardvalidator.model.TransactionApi;
import solent.ac.uk.ood.examples.cardvalidator.model.TransactionResult;

/**
 *
 * @author cgallen
 */
public class TransactionApiImpl implements TransactionApi {

    private Integer transactionId = 0;

    private static final Logger LOG = LoggerFactory.getLogger(TransactionApiImpl.class);

    private static final Logger TRANSACTIONLOG = LoggerFactory.getLogger("transaction-log");

    private final CardFactoryDAO cardFactoryDao;

    private final AccountDAO accountDAO;

    public TransactionApiImpl(CardFactoryDAO cardFactoryDao, AccountDAO accountDAO) {
        this.cardFactoryDao = cardFactoryDao;
        this.accountDAO = accountDAO;
    }

    @Override
    public TransactionResult requestTransaction(Transaction requestTransaction) {
        // created new transactionId
        TransactionResult tresult = new TransactionResult();
        
        synchronized (this){
            transactionId = transactionId + 1;
        }
        
        requestTransaction.setTransactionId(transactionId);
        tresult.setTransactionRequest(requestTransaction);

        CreditCard shopCard = requestTransaction.getFromCard();
        CreditCard customerCard = requestTransaction.getToCard();

        CreditCardFactoryAndValidator shopCardValidator = cardFactoryDao.getCreditCardFactoryAndValidator(shopCard.getIssuerIdentificationNumber());
        CreditCardFactoryAndValidator customerCardValidator = cardFactoryDao.getCreditCardFactoryAndValidator(customerCard.getIssuerIdentificationNumber());

        try {
            boolean shopLunnValid = shopCardValidator.cardNumberLunnIsValid(shopCard);

            boolean shopCvvValid = shopCardValidator.cvvIsValid(shopCard);

            boolean customerLunnValid = customerCardValidator.cardNumberLunnIsValid(customerCard);

            boolean customerCvvValid = customerCardValidator.cvvIsValid(customerCard);

            if (!shopCvvValid || !shopLunnValid || !customerCvvValid || !customerLunnValid) {
                String debuginfo = "";
                if (!shopCvvValid) {
                    debuginfo = debuginfo + "Shop CVV is invalid";
                }
                if (!shopLunnValid) {
                    debuginfo = debuginfo + "Shop Lunn is invalid";
                }
                if (!customerCvvValid) {
                    debuginfo = debuginfo + "Customer CVV is invalid";
                }
                if (!customerLunnValid) {
                    debuginfo = debuginfo + "Customer Lunn is invalid";
                }
                tresult.setResultCode(ResultCode.INVALID_CARD);
                tresult.setDebugInfo(debuginfo);
                return tresult;
            }

        } catch (Exception ex ){
           tresult.setResultCode(ResultCode.INVALID_CARD);
                tresult.setDebugInfo(ex.toString());
                return tresult;
        }
        
        Account shopAccount = accountDAO.retrieveAccount(shopCard.getIssuerIdentificationNumber(), shopCard.getIndividualAccountIdentifier());
        Account customerAccount = accountDAO.retrieveAccount(customerCard.getIssuerIdentificationNumber(), customerCard.getIndividualAccountIdentifier());
        
        if(shopAccount == null){
                tresult.setResultCode(ResultCode.INVALID_CARD);
                tresult.setDebugInfo("Shop account not found");
                return tresult;
        }
        
        if(customerAccount == null){
                tresult.setResultCode(ResultCode.INVALID_CARD);
                tresult.setDebugInfo("Customer account not found");
                return tresult;
        }
        
        Double customerBalance = customerAccount.getBalance();
        Double transactionAmount = requestTransaction.getAmount();
        Double shopBalance = shopAccount.getBalance();
        
        if (customerAccount.getBalance() - requestTransaction.getAmount() < 0){
            tresult.setResultCode(ResultCode.DECLINED);
            tresult.setDebugInfo("Insufficient funds");
            return tresult;
        }
        
        customerAccount.setBalance(customerBalance - transactionAmount);
        shopAccount.setBalance(shopBalance + transactionAmount);
        
        accountDAO.updateAccount(shopAccount);
        accountDAO.updateAccount(customerAccount);
        
        tresult.setResultCode(ResultCode.APPROVED);
        
        return tresult;

    }

}
