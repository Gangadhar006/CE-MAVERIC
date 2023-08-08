package com.maveric.currencyexchange.repository;

import com.maveric.currencyexchange.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISecurityRepository extends JpaRepository<Security,Long> {
}
