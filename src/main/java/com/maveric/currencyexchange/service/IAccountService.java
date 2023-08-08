package com.maveric.currencyexchange.service;

import com.maveric.currencyexchange.dtos.AccountDto;

import java.util.List;

public interface IAccountService {
    AccountDto createAccount(Long accountId, AccountDto accountDto);

    AccountDto updateAccount(Long accountId, AccountDto accountDto);

    AccountDto deleteAccount(Long accountId);

    List<AccountDto> findAllAccounts(Long id);
}
