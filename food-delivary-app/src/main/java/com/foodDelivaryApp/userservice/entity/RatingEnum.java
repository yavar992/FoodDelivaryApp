package com.foodDelivaryApp.userservice.entity;


public enum RatingEnum {

    ONE_STAR(1, "Poor"),
    TWO_STARS(2, "Below Average"),
    THREE_STARS(3, "Average"),
    FOUR_STARS(4, "Good"),
    FIVE_STARS(5, "Excellent");

    private int rating;
    private String message;

    RatingEnum(int rating, String message) {
        this.rating = rating;
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public String getMessage() {
        return message;
    }


    public static RatingEnum fromRating(Integer value) throws IllegalAccessException {
        for(RatingEnum rating : RatingEnum.values()){
            if (rating.getRating()==value){
                return  rating;
            }
        }
        throw new IllegalAccessException("Invalid rating value: " + value);
    }


}
