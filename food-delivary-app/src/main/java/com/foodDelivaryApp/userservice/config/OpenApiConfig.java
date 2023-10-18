package com.foodDelivaryApp.userservice.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "yavar ali khan",
                        email ="yavarkhan892300@gmail.com"
                ) ,
                description =  "Rest Api for the Online Food Delivary ",
                title ="Online food delivary backend API's",
                version = "3"
        ),
        servers = {
                @Server(
                        description = "LOCAL ENV",
                        url ="localhost:8082"
                ),
                @Server(
                        description =  "PROD ENV",
                        url = "localhost:8082/**"
                )
        }
)
public class OpenApiConfig {
}
