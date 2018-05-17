package com.example.nordicmotorhomes.utilities;

import java.time.LocalDate;

public class PriceCalculator {
    private static int[] lowMonths = {1, 2, 3, 10};
    private static int[] midMonths = {4, 5, 9, 11};
    private static int[] highMonths = {6, 7, 8, 12};
    private static double high = 2.08;
    private static double mid = 1.3;

    //calculates price for the season
    public static double GetPrice(double price){
        double setPrice = price;
        LocalDate date = LocalDate.now();

        int month = date.getMonthValue();

        for(int i : lowMonths){
            if(i == month){
                return price;
            }
        }

        for(int i : midMonths){
            if(i == month){
                return price * mid;
            }
        }

        for(int i : highMonths){
            if(i == month){
                return price * high;
            }
        }
        return 0;
    }
}
