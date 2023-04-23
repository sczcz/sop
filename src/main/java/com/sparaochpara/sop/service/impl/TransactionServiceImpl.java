package com.sparaochpara.sop.service.impl;

import com.sparaochpara.sop.dto.TransactionDto;
import com.sparaochpara.sop.model.Transaction;
import com.sparaochpara.sop.repository.TransactionRepository;
import com.sparaochpara.sop.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    @Override
    public List<TransactionDto> findAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map((transaction) -> mapToTransactionDto(transaction)).collect(Collectors.toList());
    }
    private TransactionDto mapToTransactionDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .category(transaction.getCategory())
                .createdOn(transaction.getCreatedOn())
                .updatedOn(transaction.getUpdatedOn())
                .user(transaction.getUser())
                .group(transaction.getGroup())
                .build();
    }
}
