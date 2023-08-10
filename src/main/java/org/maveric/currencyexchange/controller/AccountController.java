package org.maveric.currencyexchange.controller;

import org.maveric.currencyexchange.dtos.AccountDto;
import org.maveric.currencyexchange.service.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers/{customerId}/accounts")
public class AccountController {
    private IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@PathVariable("customerId") Long customerId,
                                                    @RequestBody AccountDto accountDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(customerId, accountDto));
    }

    @PatchMapping(value = "/{accountId}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable("customerId") Long customerId,
                                                    @PathVariable("accountId") Long accountId,
                                                    @RequestBody AccountDto accountDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.updateAccount(customerId, accountId, accountDto));
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> findAllAccounts(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.findAllAccounts(customerId));
    }

    @DeleteMapping(value = "/{accountId}")
    public ResponseEntity<AccountDto> deleteAccount(@PathVariable("customerId") Long customerId,
                                                    @PathVariable("accountId") Long accountId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(accountService.deleteAccount(customerId, accountId));
    }
}