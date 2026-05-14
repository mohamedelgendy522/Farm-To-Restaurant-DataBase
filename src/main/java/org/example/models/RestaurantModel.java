package org.example.models;

public class RestaurantModel {

    private int restaurantId;

    private String restaurantName;

    private String city;

    private String street;



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

}