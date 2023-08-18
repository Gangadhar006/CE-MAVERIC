package org.maveric.currencyexchange.controller;

import jakarta.validation.Valid;
import org.maveric.currencyexchange.payload.AccountRequest;
import org.maveric.currencyexchange.payload.AccountResponse;
import org.maveric.currencyexchange.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers/{customerId}/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@PathVariable long customerId,
                                                         @Valid @RequestBody AccountRequest accountRequest) {
        logger.info("Requesting account creation");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(customerId, accountRequest));
    }

    @PutMapping(value = "/{accountId}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable long customerId,
                                                         @PathVariable long accountId,
                                                         @Valid @RequestBody AccountRequest accountRequest) {
        logger.info("Requesting account updation");
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.updateAccount(customerId, accountId, accountRequest));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAllAccounts(@PathVariable long customerId) {
        logger.info("Requesting account fetching");
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.findAllAccounts(customerId));
    }

    @DeleteMapping(value = "/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable("customerId") long customerId,
                                                @PathVariable("accountId") long accountId) {
        logger.info("Requesting account deletion");
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.deleteAccount(customerId, accountId));
    }
}