package ee.laus.banking.app.transaction;

import ee.laus.banking.app.transaction.dto.TransactionCreateDto;
import ee.laus.banking.infrastructure.transaction.TransactionService;
import ee.laus.banking.presentation.transaction.TransactionListResponse;
import ee.laus.banking.presentation.transaction.TransactionResultResponse;
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
