package com.banking.mapper;

import com.banking.dto.TransferDto;
import com.banking.entity.Transfer;

public class TransferMapper {

   public static Transfer mapToTransfer(TransferDto transferDto) {
	   
	   Transfer transfer = new Transfer(
			   
			   transferDto.getId(),
			   transferDto.getFromAccount(),
			   transferDto.getToAccount(),
			   transferDto.getAmount(),
			   transferDto.getDescription(),
			   transferDto.getStatus(),
			   transferDto.getCreatedAt()
			   
			   );
	   
	   return transfer;	   
   }
   
   public static TransferDto mapToTransferDto (Transfer transfer) {
	   
	   TransferDto transferDto =new TransferDto(
			   transfer.getId(),
               transfer.getFromAccount(),
               transfer.getToAccount(),
               transfer.getAmount(),
               transfer.getDescription(),
               transfer.getStatus(),
               transfer.getCreatedAt()
			   
			   );
	   return transferDto;
   }
}
