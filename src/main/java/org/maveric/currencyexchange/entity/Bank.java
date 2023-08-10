//package com.maveric.currencyexchange.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//
//@Entity
//public class Bank {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    @Column(unique = true, nullable = false)
//    private String name;
//    @Column(nullable = false)
//    private String branch;
//    @Column(unique = true, nullable = false)
//    private String ifsc;
//
//    @ManyToMany(mappedBy = "banks")
//    private List<Country> countries;
//
//    @OneToMany(mappedBy = "bank")
//    private List<Account> accounts;
//}