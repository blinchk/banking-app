package ee.laus.banking.controller;

import ee.laus.banking.dto.AccountCreateDto;
import ee.laus.banking.service.AccountService;
import ee.laus.banking.response.AccountResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public AccountResultResponse create(@RequestBody AccountCreateDto dto) {
        return accountService.create(dto);
    }

    @GetMapping("/{id}")
    public AccountResultResponse byId(@PathVariable Long id) {
        return accountService.get(id);
    }
}
