package org.maveric.currencyexchange.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class AccountNumberGenerator implements IdentifierGenerator {

    private static final String PREFIX = "ACCN";

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
        return PREFIX + "-" + uuid;
    }
}

