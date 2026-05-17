package org.example.models;

public class RestaurantModel {

    private int restaurantId;

    private String restaurantName;

    private String city;

    private String street;

    private int orderId;

    private String orderDate;

    private double totalAmount;



    public RestaurantModel(
            int restaurantId,
            String restaurantName,
            String city,
            String street

    ) {

        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.city = city;
        this.street = street;

    }

    public RestaurantModel(
            String restaurantName,
            int orderId,
            String orderDate,
            double totalAmount
    ) {

        this.restaurantName = restaurantName;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getOrderId() { return orderId; }

    public String getOrderDate() { return orderDate; }

    public double getTotalAmount() { return totalAmount; }

}