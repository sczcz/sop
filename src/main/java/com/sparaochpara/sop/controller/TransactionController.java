package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.CategoryDto;
import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.dto.TransactionDto;
import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.model.Category;
import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.Transaction;
import com.sparaochpara.sop.model.User;
import com.sparaochpara.sop.repository.CategoryRepository;
import com.sparaochpara.sop.repository.GroupRepository;
import com.sparaochpara.sop.repository.TransactionRepository;
import com.sparaochpara.sop.repository.UserRepository;
import com.sparaochpara.sop.service.CategoryService;
import com.sparaochpara.sop.service.GroupService;
import com.sparaochpara.sop.service.TransactionService;
import com.sparaochpara.sop.service.UserService;
import com.sparaochpara.sop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService, CategoryRepository categoryRepository, GroupService groupService, GroupRepository groupRepository, TransactionRepository transactionRepository){
        this.transactionService = transactionService;
        this.userService=userService;
        this.categoryRepository=categoryRepository;
        this.groupService=groupService;
        this.groupRepository=groupRepository;
        this.transactionRepository=transactionRepository;
    }

    @GetMapping("/transactions")
    public String listUserTransactions(@AuthenticationPrincipal UserDetails userDetails, Model model){
        String userEmail = userDetails.getUsername();
        List<TransactionDto> transactions = transactionService.findTransactionsByUserEmail(userEmail);
        String firstName = userRepository.findUserByEmail(userEmail).getFirstName();
        model.addAttribute("transactions", transactions);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("firstName", firstName);
        return "transactions-list";
    }

    @PostMapping("/transactions/{transactionId}/remove")
    public String deleteTransaction(@PathVariable("transactionId") Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return "redirect:/users";
    }

    @GetMapping("/ass")
    @ResponseBody
    public Map<String, Object> transactionsPieChart(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        List<Transaction> transactions = transactionRepository.findByUserEmail(userDetails.getUsername());
        //List<TransactionDto> transactions = transactionService.findAllTransactions();
        List<CategoryDto> categories = categoryService.findAllCategories();
        Map<String, Double> dataMap = new HashMap<>();
        double totalAmount = 0.0;

        for (CategoryDto category : categories) {
            double categoryAmount = 0.0;

            for (Transaction transaction : transactions) {
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

        Map<String, Object> response = new HashMap<>();
        response.put("dataMap", dataMap);
        response.put("totalAmount", totalAmount);

        return response;
    }

    @PostMapping("/transactionsSaved")
    public String saveTransaction(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam("amount") double amount,
                                  @RequestParam("description") String description,
                                  @RequestParam("group-dropdown") Long groupId,
                                  @RequestParam("category-dropdown") Long categoryId){

       UserDto userDto =  userService.findUserByEmail(userDetails.getUsername());
       User user = User.builder()
               .email(userDto.getEmail())
               .firstName(userDto.getFirstName())
               .lastName(userDto.getLastName())
               .password(userDto.getPassword())
               .createdOn(userDto.getCreatedOn())
               .updatedOn(userDto.getUpdatedOn())
               .build();

       GroupDto groupDto = groupService.findGroupById(groupId);
       Group group = Group.builder()
                .id(groupDto.getId())
                .createdOn(groupDto.getCreatedOn())
                .name(groupDto.getName())
                .updatedOn(groupDto.getUpdatedOn())
                .build();

       CategoryDto categoryDto = categoryService.findCategoryById(categoryId);
       Category category = Category.builder()
               .id(categoryDto.getId())
               .name(categoryDto.getName())
               .build();

       TransactionDto transactionDto = TransactionDto.builder()
               .description(description)
               .amount(amount)
               .user(user)
               .group(group)
               .category(category)
               .build();

        transactionService.saveTransaction(transactionDto);

        return "redirect:/users";
    }

    @GetMapping("/groupTrans/{groupId}")
    @ResponseBody
    public Map<String, Object> transactionsGroupPieChart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long groupId, Model model) {

        //User user = userRepository.findUserByEmail(userDetails.getUsername());
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Group group = groupOptional.get();
        System.out.println(groupId);

        List<Transaction> transactions = transactionRepository.findByGroup(group);
        //List<CategoryDto> categories = categoryService.findAllCategories();
        List<Category> categories = categoryRepository.findAll();
        Map<String, Double> groupTrans = new HashMap<>();
        double totalAmount = 0.0;

        for (Category category : categories) {
            double categoryAmount = 0.0;

            for (Transaction transaction : transactions) {
                if (transaction.getCategory().getId() == category.getId()) {
                    categoryAmount += transaction.getAmount();
                    totalAmount += transaction.getAmount();
                    System.out.println(transaction.getDescription());
                }
            }

            if (categoryAmount > 0) {
                groupTrans.put(category.getName(), categoryAmount);
            }
        }
        model.addAttribute("groupTrans", groupTrans);
        model.addAttribute("totalAmount", totalAmount);

        Map<String, Object> response = new HashMap<>();
        response.put("dataMap", groupTrans);
        response.put("totalAmount", totalAmount);

        return response;
    }

 /*   @GetMapping("/groupTransTable")
    public String showTransactions(@RequestParam(name = "groupId", required = false) Long groupId, Model model) {
        // Logic to fetch transactions based on the selected group (groupId)
        // If groupId is null or empty, fetch all transactions for the user

        List<Transaction> transactions;
        if (groupId != null && groupId > 0) {
            Group group = groupService.getGroupById(groupId); // Assuming you have a GroupService
            transactions = transactionService.getTransactionsByGroup(group); // Assuming you have a TransactionService
        } else {
            transactions = transactionService.getTransactionsForUser(currentUser); // Assuming you have a TransactionService and currentUser object
        }*
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Group group = groupOptional.get();
        List<Transaction> transactions = transactionRepository.findTopNByGroupOrderByCreatedOnDesc(group, 30);


        model.addAttribute("userTransactions", transactions);
        return "groupTransactionTable::table"; // Return the fragment name that represents the table HTML content
    }
*/




}
