package com.maveric.currencyexchange.serviceImpl;

import com.maveric.currencyexchange.dtos.AccountDto;
import com.maveric.currencyexchange.service.IAccountService;
import org.modelmapper.ModelMapper;

import java.util.List;

public class AccountServiceImpl implements IAccountService {
    private ModelMapper mapper;

    @Override
    public AccountDto createAccount(Long accountId, AccountDto accountDto) {

    }

    @Override
    public AccountDto updateAccount(Long accountId, AccountDto accountDto) {
        return null;
    }

    @Override
    public AccountDto deleteAccount(Long accountId) {
        return null;
    }

    @Override
    public List<AccountDto> findAllAccounts(Long id) {
        return null;
    }
}
