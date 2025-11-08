package com.banking.service;

import java.util.List;

import com.banking.dto.TransferDto;

public interface TransferService {
	
	TransferDto createTransfer(TransferDto transferDto);
    TransferDto getTransferById(Long id);
    List<TransferDto> getAllTransfers();
    void deleteTransfer(Long id);

}
