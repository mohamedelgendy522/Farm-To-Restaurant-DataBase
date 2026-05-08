package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {



        InquiryService obj = new InquiryService();
        obj.GetTopCropType();
        obj.GetInactiveFarmsLastMonth();
        obj.GetTopDriverLastMonth();
        obj.GetRestaurantsWithoutOrders();
        obj.GetRestaurantDeliveredBatches();
        obj.GetFarmRevenue();


    }
}