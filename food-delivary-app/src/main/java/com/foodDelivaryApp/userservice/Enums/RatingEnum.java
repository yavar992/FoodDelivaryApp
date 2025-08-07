package com.foodDelivaryApp.userservice.Enums;


import lombok.Getter;

@Getter
public enum RatingEnum {

    ONE_STAR(1, "Poor"),
    TWO_STARS(2, "Below Average"),
    THREE_STARS(3, "Average"),
    FOUR_STARS(4, "Good"),
    FIVE_STARS(5, "Excellent");

    private final int rating;
    private final String message;

    RatingEnum(int rating, String message) {
        this.rating = rating;
        this.message = message;
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
