package com.foodDelivaryApp.userservice.util;

import com.foodDelivaryApp.userservice.dto.Address;
import com.foodDelivaryApp.userservice.entity.MenuItem;
import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.exceptionHandling.DeliveryException;
import com.foodDelivaryApp.userservice.repository.MenuItemRepo;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.IMenuItemService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PayPalUtil {

    @Autowired
    private APIContext apiContext;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private IMenuItemService menuItemService;

    @Autowired
    private MenuItemRepo menuItemRepo;

    public static final String SUCCESS_URL = "https://bard.google.com/chat/2938ecc0e54abb6";
    public static final String CANCEL_URL = "https://khouloudkhezami.medium.com/integrating-paypal-payment-in-springboot-backend-full-guide-with-all-the-steps-charge-and-payout-413013a993b3";
    private static final String INTENT = "sale";
    private static final String METHOD = "paypal";

    private static final String SENDER_BATCH = "yavar3492";
    private static final String EMAIL_SUBJECT = "yavarkhan892300@gmail.com";
    private static final String RECIPIENT_TYPE = "receiver";




    public Payment createPayment(Long itemId ,Double total, String currency, String description , Double TAX , Double shippingCharge , Address address) throws PayPalRESTException {
        MenuItem menuItem = menuItemService.findById(itemId);
        Restaurant restaurant = menuItem.getMenu().getRestaurant();
        List<String> deliveryZones = restaurant.getDeliveryZones();
        if (!deliveryZones.contains(address.getPostalCode())){
            throw new DeliveryException("Sorry ! we are not available in your address");
        }

        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));
        Details details = new Details();
        TAX = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        shippingCharge = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        details.setFee(String.format("%.2f",TAX));
        details.setShipping(String.format("%.2f",shippingCharge));
        amount.setDetails(details);
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(METHOD);

        com.paypal.api.payments.Address address1 = new com.paypal.api.payments.Address();
        address1.setPhone(address.getPhoneNumber());
        address1.setType(address.getAddressTypes());
        address1.setCountryCode(address.getCountryCode());
        address1.setLine2(address.getLine2());
        address1.setLine1(address.getLine1());
        address1.setCity(address.getCity());
        address1.setPostalCode(address.getPostalCode());
        address1.setState(address.getState());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepo.findByEmail(userDetails.getUsername());
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(user.getFirstName());
        payerInfo.setLastName(user.getLastName());
        payerInfo.setEmail(user.getEmail());
        payerInfo.setPhone(user.getPhoneNumber());
        payerInfo.setBillingAddress(address1);



        Payment payment = new Payment();
        payment.setIntent(INTENT);
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(CANCEL_URL);
        redirectUrls.setReturnUrl(SUCCESS_URL);
        payment.setRedirectUrls(redirectUrls);
        double popularityOnEachOrder = 100.0;
        menuItem.setPopularity(menuItem.getPopularity()+popularityOnEachOrder);
        menuItem.setSellCount(menuItem.getSellCount()+1);
        menuItemRepo.saveAndFlush(menuItem);
        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    public PayoutBatch payout(double total, String currency, String receiverEmail) throws PayPalRESTException {
        Date currentDate = new Date(System.currentTimeMillis());
        PayoutSenderBatchHeader payoutSenderBatchHeader = new PayoutSenderBatchHeader();
        payoutSenderBatchHeader.setSenderBatchId(SENDER_BATCH + " " + currentDate.toString());
        payoutSenderBatchHeader.setEmailSubject(EMAIL_SUBJECT);
        payoutSenderBatchHeader.setRecipientType(RECIPIENT_TYPE);
        List<PayoutItem> payoutItems = new ArrayList<>();

        payoutItems.add(new PayoutItem(new Currency(currency, String.format("%.2f", total)), receiverEmail));
        Payout payout = new Payout();

        payout.setSenderBatchHeader(payoutSenderBatchHeader);
        payout.setItems(payoutItems);

        return payout.create(apiContext, null);
    }

}
