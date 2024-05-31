package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.entity.*;
import com.foodDelivaryApp.userservice.entity.Address;
import com.foodDelivaryApp.userservice.event.OrderConfirmationDetailsEvent;
import com.foodDelivaryApp.userservice.repository.*;
import com.foodDelivaryApp.userservice.util.GeneratedRandomNumber;
import com.foodDelivaryApp.userservice.util.OTPUtil;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PaypalService {

    @Autowired
    private APIContext apiContext;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private OrderConfirmationRepo orderConfirmationRepo;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UserRepo userRepo;
    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total)); // 9.99$ - 9,99€

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

        Payment payment1 =  payment.create(apiContext);
        for (Links links: payment1.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                System.out.println("here i can do the work that i want !");
            }
        }
        return payment1;
    }


    public Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }


    public String populateItemIntoDatabase(Long cartId, String foodCode) {
        Optional<CartItem> cartItem = cartItemRepo.findById(cartId);
        if (cartItem.isEmpty()){
            return "no cart item found for id " + cartId;
        }
        CartItem cartItem1 = cartItem.get();
        MenuItem menuItem = cartItem1.getMenuItem();
        menuItem.setSellCount(menuItem.getSellCount() +1);
        menuItem.setPopularity(menuItem.getPopularity() + 100);

//        menuItemRepo.saveAndFlush(menuItem); // menuItem fields got populated successfully

        //now comes to the orderConfirmationDetails set the data in the orderConfirmationDetails entity and then make a event and a listner that will
        // listean to this event and send a email o the user with the mandatory details
        Optional<User> user = userRepo.findById(cartItem1.getUserId());
        Address address =  addressRepo.findByUserIdWhereDefaultAddressIs(cartItem1.getUserId());
        OrderConfirmationDetails orderConfirmationDetails = new OrderConfirmationDetails();
        orderConfirmationDetails.setName(menuItem.getName());
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        orderConfirmationDetails.setOrderDate(localDate);
        orderConfirmationDetails.setQuantity(cartItem1.getQuantity());
        orderConfirmationDetails.setPrice(cartItem1.getPrice());
        orderConfirmationDetails.setOrderNumber(GeneratedRandomNumber.generateReferralCode(12)+ OTPUtil.random3Digit());
        orderConfirmationDetails.setTotalAmountPaid(cartItem1.getPrice());
        orderConfirmationDetails.setDeliveryTimeEstimate("");
        orderConfirmationDetails.setDefaultDeliveryAddress(address);
        orderConfirmationDetails.setPaymentMethod("PAYPAL");
        orderConfirmationDetails.setContactEmail(menuItem.getMenu().getRestaurant().getEmail());
        orderConfirmationDetails.setContactPhone(menuItem.getMenu().getRestaurant().getPhoneNumber());
        orderConfirmationDetails.setTrackingUrl("");
        orderConfirmationDetails.setUser(user.get());
        // saves the order confirmation details
        OrderConfirmationDetailsEvent orderConfirmationDetailsEvent = new OrderConfirmationDetailsEvent(orderConfirmationDetails);
        applicationEventPublisher.publishEvent(orderConfirmationDetailsEvent);
        orderConfirmationRepo.saveAndFlush(orderConfirmationDetails);
        menuItem.setOrderConfirmationDetails(orderConfirmationDetails);
        menuItemRepo.saveAndFlush(menuItem);
        cartItemRepo.delete(cartItem1);
        return " order confirmation details populated successfully !!";
    }
}
