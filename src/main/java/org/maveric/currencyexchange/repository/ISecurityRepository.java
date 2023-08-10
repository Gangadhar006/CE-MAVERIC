package org.maveric.currencyexchange.repository;

import org.maveric.currencyexchange.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISecurityRepository extends JpaRepository<Security,Long> {
}
