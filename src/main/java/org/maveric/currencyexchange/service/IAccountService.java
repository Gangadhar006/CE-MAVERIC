package org.maveric.currencyexchange.service;

import org.maveric.currencyexchange.dtos.AccountDto;

import java.util.List;

public interface IAccountService {
    AccountDto createAccount(Long accountId, AccountDto accountDto);

    AccountDto updateAccount(Long customerId, Long accountId, AccountDto accountDto);

    AccountDto deleteAccount(Long customerId,Long accountId);

    List<AccountDto> findAllAccounts(Long id);
}
