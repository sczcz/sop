package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.CategoryDto;
import com.sparaochpara.sop.dto.TransactionDto;
import com.sparaochpara.sop.repository.TransactionRepository;
import com.sparaochpara.sop.repository.UserRepository;
import com.sparaochpara.sop.service.CategoryService;
import com.sparaochpara.sop.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public TransactionController(TransactionService transactionService){this.transactionService = transactionService;}

    @GetMapping("/transactions")
    public String listTransactions(Model model){
        List<TransactionDto> transactions = transactionService.findAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions";
    }

    @GetMapping("{userEmail}/transactions")
    public String listUserTransactions(@PathVariable String userEmail, Model model){
        List<TransactionDto> transactions = transactionService.findTransactionsByUserEmail(userEmail);
        String firstName = userRepository.findUserByEmail(userEmail).getFirstName();
        model.addAttribute("transactions", transactions);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("firstName", firstName);
        return "transactions-list";
    }

    @GetMapping("/ass")
    public String transactionsPieChart(Model model) {
        List<TransactionDto> transactions = transactionService.findAllTransactions();
        List<CategoryDto> categories = categoryService.findAllCategories();
        Map<String, Double> dataMap = new HashMap<>();
        double totalAmount = 0.0;

        for (CategoryDto category : categories) {
            double categoryAmount = 0.0;

            for (TransactionDto transaction : transactions) {
                if (transaction.getCategory().getId() == category.getId()) {
                    categoryAmount += transaction.getAmount();
                    totalAmount += transaction.getAmount();
                }
            }

            if (categoryAmount > 0) {
                dataMap.put(category.getName(), categoryAmount);
            }
        }
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("totalAmount", totalAmount);
        return "ass";
    }


}
