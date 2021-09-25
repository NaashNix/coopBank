/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.components;

import model.CommonTransactionModel;
import model.DepositObjectModel;
import model.WithdrawObjectModel;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class TransactionOrderMaker {
    public ArrayList<CommonTransactionModel> getOrderedTransactionList(ArrayList<DepositObjectModel> depositModels, ArrayList<WithdrawObjectModel> withdrawModels) throws ParseException {
        ArrayList<String> toBeSort = new ArrayList<>();
        for (DepositObjectModel tempDeposit :depositModels
             ) {
            String date = tempDeposit.getDate();
            String time = tempDeposit.getTime();
            /*Date convertedDate = new SimpleDateFormat("ddMMyyyy").parse(date);*/
            toBeSort.add(date+time);
        }

        Collections.sort(toBeSort);
        System.out.println(toBeSort);
        return null;
    }
}
