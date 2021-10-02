package controller.components;

import controller.dbControllers.MoneyJournalController;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


public class NumberGenerator {
    public String generateNumber(){

        /*
            --> This is the algorithm for generating account number.
            --> account number format is 'ddmmssMMrr' [r - random number]
                * @returns generatedNumber as String
         */

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        String day = LocalDateTime.now().format(dayFormatter);
        String month = LocalDateTime.now().format(monthFormatter);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mmss");
        String time = LocalDateTime.now().format(formatter);
        Random random = new Random();
        String lastNumber = String.format("%02d",random.nextInt(99));
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
        String year = LocalDateTime.now().format(yearFormatter);
        String firstNumbers = String.format("%02d",random.nextInt(99));

        return firstNumbers+day+time+year+month+lastNumber;

    }

    public String getTransactionID(){
        Random random = new Random();
        //return "D-" + lastNumber;
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM"));
        String day = LocalDateTime.now().format(DateTimeFormatter.ofPattern("d"));
        String year = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy"));
        String sec = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ss"));
        String hour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh"));
        String rand =  String.format("%02d",random.nextInt(99));
        return "D"+month+rand+year+sec+day+hour;
    }

    public String getWithdrawalID(){
        Random random = new Random();
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM"));
        String day = LocalDateTime.now().format(DateTimeFormatter.ofPattern("d"));
        String year = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy"));
        String sec = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ss"));
        String hour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh"));
        String rand =  String.format("%02d",random.nextInt(99));
        return "W"+month+rand+year+sec+day+hour;
    }

    public String getLoanNumber(){
        Random random = new Random();
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM"));
        String day = LocalDateTime.now().format(DateTimeFormatter.ofPattern("d"));
        String year = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy"));
        String sec = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ss"));
        String hour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh"));
        String rand =  String.format("%02d",random.nextInt(99));
        return "L"+year+rand+month+sec+day+hour;
    }

    public String getIncomeTransactionID(){
        Random random = new Random();
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM"));
        String day = LocalDateTime.now().format(DateTimeFormatter.ofPattern("d"));
        String year = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy"));
        String sec = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ss"));
        String hour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh"));
        String rand =  String.format("%02d",random.nextInt(99));
        return "I"+rand+year+sec+hour+month+day;
    }

    public String getNumberForExpenditure(){
        Random random = new Random();
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM"));
        String day = LocalDateTime.now().format(DateTimeFormatter.ofPattern("d"));
        String year = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy"));
        String sec = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ss"));
        String hour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh"));
        String rand =  String.format("%02d",random.nextInt(99));
        return "E"+sec+rand+day+year+hour+month;
    }


}
