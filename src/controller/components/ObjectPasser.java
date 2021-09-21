/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.components;

import model.CustomerModel;
import model.DepositObjectModel;
import model.OpenAccDepMoneyModel;
import model.WithdrawObjectModel;

public class ObjectPasser {
    private static CustomerModel customerModel;
    private static OpenAccDepMoneyModel depositModel;
    private static String accountNumber;
    private static DepositObjectModel depositObjectModel;
    private static WithdrawObjectModel withdrawObjectModel;

    public static void setModels(CustomerModel customer, OpenAccDepMoneyModel deposit){
        customerModel = customer;
        depositModel = deposit;
    }
    public static CustomerModel getCustomerModel() {
        return customerModel;
    }

    public static OpenAccDepMoneyModel getDepositModel(){
        return depositModel;
    }

    public static void setAccountNumber(String accountNumber1){
        accountNumber = accountNumber1;
    }

    public static String getAccountNumber() {
        return accountNumber;
    }

    public static void setDepositObjectModel(DepositObjectModel model){
        depositObjectModel = model;
    }

    public static DepositObjectModel getDepositObjectModel(){
        return depositObjectModel;
    }


    public static WithdrawObjectModel getWithdrawObjectModel() {
        return withdrawObjectModel;
    }

    public static void setWithdrawObjectModel(WithdrawObjectModel model) {
        ObjectPasser.withdrawObjectModel = model;
    }
}
