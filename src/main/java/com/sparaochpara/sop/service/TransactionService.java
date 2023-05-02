package com.sparaochpara.sop.service;

import com.sparaochpara.sop.dto.TransactionDto;
import com.sparaochpara.sop.model.Transaction;
import com.sparaochpara.sop.repository.TransactionRepository;

import java.util.List;

public interface TransactionService {


    List<TransactionDto>findAllTransactions();

    Transaction saveTransaction(TransactionDto transactionDto);
    public List<Transaction> getAllTransactions();
}
