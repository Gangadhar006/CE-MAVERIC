package org.maveric.currencyexchange.service;

import org.maveric.currencyexchange.payload.AccountRequest;
import org.maveric.currencyexchange.payload.AccountResponse;

import java.util.List;

public interface IAccountService {
    AccountResponse createAccount(long accountId, AccountRequest accountDto);

    AccountResponse updateAccount(long customerId, long accountId, AccountRequest accountDto);

    String deleteAccount(long customerId, long accountId);

    List<AccountResponse> findAllAccounts(long id);
}
