package com.foodDelivaryApp.userservice.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class LocalDateTypeAdaptor extends TypeAdapter<LocalDate> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;


    @Override
    public void write(JsonWriter jsonWriter, LocalDate value) throws IOException {
        if (value == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(formatter.format(value));
        }
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            String dateStr = in.nextString();
            return LocalDate.parse(dateStr, formatter);
        }
    }
}
