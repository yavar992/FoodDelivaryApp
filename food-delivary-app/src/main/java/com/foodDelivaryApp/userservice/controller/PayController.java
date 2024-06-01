package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.service.MenuItemService;
import com.foodDelivaryApp.userservice.serviceImpl.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("api/v1/payment")
@Slf4j
public class PayController {

    @Autowired
    PaypalService paypalService;

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/create")
    public RedirectView createPayment(
            @RequestParam("method") String method,
            @RequestParam("amount") String amount,
            @RequestParam("currency") String currency,
            @RequestParam("description") String description,
            @RequestParam("cartId") Long cartId ,
            @RequestParam(value = "zipCode" , required = false) String zipCode

    ) {
        try {
            String cancelUrl = "http://localhost:8082/happyMeals/api/v1/payment/cancel";
            String successUrl = "http://localhost:8082/happyMeals/api/v1/payment/success?cartId=" + cartId + "&foodCode=" + zipCode; // Pass item details in the redirect URL

            Payment payment = paypalService.createPayment(
                    Double.valueOf(amount),
                    currency,
                    method,
                    "sale",
                    description,
                    cancelUrl,
                    successUrl
            );

            for (Links links: payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error occurred:: " +  e);
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
//            @RequestParam("amount") String amount,
//            @RequestParam("currency") String currency
//            @RequestParam("description") String description
            @RequestParam("cartId") Long cartId ,
            @RequestParam("zipCode") String zipCode
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                // Payment is approved, populate item into the database
//                populateItemIntoDatabase(amount, currency, description);
                 paypalService.populateItemIntoDatabase(cartId , zipCode);
                System.out.println("the payment is being done , now i can populate the item into the database");
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error occurred:: " + e);
        }
        return "paymentError";
    }

    @GetMapping("/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "paymentError";
    }


    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/hi")
    public String hi(){
        return "hi";
    }

    @GetMapping("/checkAvailability")
    public String checkAvailability(
            @RequestParam(value = "foodCode" , required = false) String foodCode ,
            @RequestParam(value = "pincode" , required = false) String pincode,
            Model model
    ) {
        String availabilityStatus = menuItemService.isItemAvailable(foodCode, pincode);
        if ("Item not found".equals(availabilityStatus)) {
            model.addAttribute("message", "Item not found with the given ID.");
            return "itemNotFound"; // Return a view when item is not found
        } else if ("Item not available in this area".equals(availabilityStatus)) {
            model.addAttribute("message", "Item is not available in the provided pincode.");
            return "itemNotFound"; // Return a view when item is not available in the given pinCode
        } else {
            model.addAttribute("message", availabilityStatus);
            return "itemAvailability"; // Return a different view when item is found
        }
    }


}
