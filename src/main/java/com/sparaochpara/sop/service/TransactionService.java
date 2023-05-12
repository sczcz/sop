package com.sparaochpara.sop.service;

import com.sparaochpara.sop.dto.TransactionDto;
import com.sparaochpara.sop.model.Transaction;

import java.util.List;

public interface TransactionService {


    List<TransactionDto>findAllTransactions();

    List<TransactionDto> findTransactionsByUserEmail(String userEmail);

    Transaction saveTransaction(TransactionDto transactionDto);
}
