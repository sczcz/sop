package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.TransactionDto;
import com.sparaochpara.sop.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){this.transactionService = transactionService;}

    @GetMapping("/transactions")
    public String listUsers(Model model){
        List<TransactionDto> transactions = transactionService.findAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions";
    }
}
