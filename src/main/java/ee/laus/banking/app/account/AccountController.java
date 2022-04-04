package ee.laus.banking.app.account;

import ee.laus.banking.app.account.dto.AccountCreateDto;
import ee.laus.banking.infrastructure.account.AccountService;
import ee.laus.banking.presentation.account.AccountResultResponse;
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
