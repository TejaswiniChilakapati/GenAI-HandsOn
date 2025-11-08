package com.banking.controller;

import com.banking.dto.TransferDto;
import com.banking.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }
    @PostMapping
    public ResponseEntity<TransferDto> createTransfer(@RequestBody TransferDto transferDto) {
        TransferDto createdTransfer = transferService.createTransfer(transferDto);
        return ResponseEntity.ok(createdTransfer);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransferDto> getTransferById(@PathVariable Long id) {
        TransferDto transfer = transferService.getTransferById(id);
        return ResponseEntity.ok(transfer);
    }
    @GetMapping
    public ResponseEntity<List<TransferDto>> getAllTransfers() {
        List<TransferDto> transfers = transferService.getAllTransfers();
        return ResponseEntity.ok(transfers);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransfer(@PathVariable Long id) {
        transferService.deleteTransfer(id);
        return ResponseEntity.ok("Transfer with ID " + id + " deleted successfully.");
    }
}
