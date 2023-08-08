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
//public class Country {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    @Column(unique = true, nullable = false)
//    private String name;
//    @Column(unique = true, nullable = false)
//    private String currency;
//
//    @ManyToMany
//    private List<Bank> banks;
//}