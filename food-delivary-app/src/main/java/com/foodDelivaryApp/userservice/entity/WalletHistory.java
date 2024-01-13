package com.foodDelivaryApp.userservice.entity;

import com.paypal.api.payments.Transaction;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@DynamicUpdate
public class WalletHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;

    private String amount;

    private LocalDate transaction_date;

    @Enumerated(EnumType.STRING)
    private TransactionEnum transaction;

    @Enumerated(EnumType.STRING)
    private WalletMethodEnum walletMethod;

    @OneToOne
    @JoinColumn(name = "user")
    private User user;


    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;



}
