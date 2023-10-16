package com.foodDelivaryApp.userservice.entity;

public enum CuisineType  {

    ITALIAN,
    MEXICAN,
    CHINESE,
    INDIAN,
    JAPANESE,
    THAI,
    GREEK,
    FRENCH,
    SPANISH,
    LEBANESE,
    KOREAN,
    VIETNAMESE,
    MOROCCAN,
    ETHIOPIAN,
    AMERICAN,
    BRAZILIAN,
    TURKISH,
    PERUVIAN,
    CAJUN_CREOLE,
    CARIBBEAN;

    public static CuisineType fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid CuisineType " + value);
        }
    }
}
