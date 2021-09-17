/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.components;

import model.CustomerModel;
import model.OpenAccDepMoneyModel;

public class OpenAccountObjectPasser {
    private static CustomerModel customerModel;
    private static OpenAccDepMoneyModel depositModel;

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

}
