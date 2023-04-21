package com.sparaochpara.sop.service;

import com.sparaochpara.sop.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto>findAllTransactions();
}
