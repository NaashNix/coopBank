package controller.components;

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
        long lastNumber = 1452369;
        lastNumber += 3;
        return "D-" + lastNumber;
    }

    public String getWithdrawalID(){
        long lastNumber = 3548961;
        lastNumber+= 3;
        return "W-"+lastNumber;
    }

}
