package com.sparaochpara.sop.service.impl;

import com.sparaochpara.sop.dto.TransactionDto;
import com.sparaochpara.sop.model.Transaction;
import com.sparaochpara.sop.repository.TransactionRepository;
import com.sparaochpara.sop.repository.UserRepository;
import com.sparaochpara.sop.service.TransactionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }
    @Override
    public List<TransactionDto> findAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        transactions.sort(Comparator.comparingLong(Transaction :: getId));
        return transactions.stream().map((transaction) -> mapToTransactionDto(transaction)).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findTransactionsByUserEmail(String userEmail) {
        List<Transaction> transactions = transactionRepository.findByUserEmail(userEmail);
        return transactions.stream().map((transaction) -> mapToTransactionDto(transaction)).collect(Collectors.toList());
    }

    @Override
    public Transaction saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = mapToTransaction(transactionDto);
        return transactionRepository.save(transaction);
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

    private Transaction mapToTransaction(TransactionDto transactionDto) {


        return Transaction.builder()
                .id(transactionDto.getId())
                .description(transactionDto.getDescription())
                .amount(transactionDto.getAmount())
                .category(transactionDto.getCategory())
                .createdOn(transactionDto.getCreatedOn())
                .updatedOn(transactionDto.getUpdatedOn())
                .user(transactionDto.getUser())
                .group(transactionDto.getGroup())
                .build();
    }
}
