package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.Address;
import com.foodDelivaryApp.userservice.DTO.MenuItemDTO;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealConstant;
import com.foodDelivaryApp.userservice.service.MenuItemService;
import com.foodDelivaryApp.userservice.service.PaymentService;
import com.foodDelivaryApp.userservice.util.PayPalUtil;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PayPalUtil payPalUtil;

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping("/")
    public String index(){
        return "index";
    }



    @PostMapping("{itemId}/checkout")
    public ResponseEntity<?> checkout(@PathVariable("itemId") Long itemId , @RequestBody Address address){
        try {
            MenuItemDTO menuItem = menuItemService.findMenuItemById(itemId);
            Double TAX = menuItem.getPrice()*2/100;
            Double shippingCharge = 40.0;
            Double totalPrice = menuItem.getPrice() + TAX + shippingCharge;
                Payment payment  = payPalUtil.createPayment(itemId , totalPrice,"USD" , menuItem.getDescription() ,TAX , shippingCharge , address);
                if (payment != null){
                     paymentService.createOrder(menuItem.getName(), menuItem.getPrice() , menuItem.getDescription() , shippingCharge , TAX ,totalPrice );
                    for(Links link:payment.getLinks()) {
                        if(link.getRel().equals("approval_url")) {
                            return ResponseEntity.status(HttpStatus.OK).body("redirect:"+link.getHref());
                        }
                    }
                }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                //i want to populate my entity here into the db , because in the /payment/create api a user can desperately exit the payment page
                //
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error occurred:: " +  e);
        }
        return "paymentSuccess";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "paymentError";
    }


}
