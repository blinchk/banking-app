package ee.laus.banking.controller;

import ee.laus.banking.dto.TransactionCreateDto;
import ee.laus.banking.service.TransactionService;
import ee.laus.banking.response.TransactionListResponse;
import ee.laus.banking.response.TransactionResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public TransactionListResponse allByAccountId(@PathVariable Long id) {
        return transactionService.allByAccountId(id);
    }

    @PostMapping
    public TransactionResultResponse create(@RequestBody @Valid TransactionCreateDto dto) {
        return transactionService.create(dto);
    }
}
