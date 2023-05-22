package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.CategoryDto;
import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.dto.GroupMemberDto;
import com.sparaochpara.sop.model.*;
import com.sparaochpara.sop.repository.GroupMemberRepository;
import com.sparaochpara.sop.repository.GroupRepository;
import com.sparaochpara.sop.repository.TransactionRepository;
import com.sparaochpara.sop.repository.UserRepository;
import com.sparaochpara.sop.service.GroupMemberService;
import com.sparaochpara.sop.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller

public class GroupController {
    private GroupService groupService;
    private GroupMemberService groupMemberService;
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private GroupMemberRepository groupMemberRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public GroupController (GroupService groupService, GroupMemberService groupMemberService, UserRepository userRepository, GroupRepository groupRepository, GroupMemberRepository groupMemberRepository, TransactionRepository transactionRepository) {
        this.groupService = groupService;
        this.groupMemberService = groupMemberService;
        this.userRepository = userRepository;
        this.groupRepository=groupRepository;
        this.groupMemberRepository=groupMemberRepository;
        this.transactionRepository=transactionRepository;
    }
    @GetMapping ("{userEmail}/groups/{groupId}")
    public String groupDetail(@PathVariable("groupId") Long groupId, Model model) {
        GroupDto groupDto = groupService.findGroupById(groupId);
        model.addAttribute ("group", groupDto);
        return "groups-detail";
    }
    @GetMapping ("/groups")
    public String groupList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        else {
            String userEmail = userDetails.getUsername();
            List<GroupDto> groups = groupMemberService.findGroupsByUserEmail(userEmail);
            String firstName = userRepository.findUserByEmail(userEmail).getFirstName();
            model.addAttribute("groups", groups);
            model.addAttribute("userEmail", userEmail);
            model.addAttribute("firstName", firstName);
            return "groups-list";
        }
    }

    @GetMapping("/groups/new")
    public String createGroupForm(@AuthenticationPrincipal UserDetails userDetails, Model model){
        String userEmail = userDetails.getUsername();
        Group group = new Group();
        model.addAttribute("group", group);
        model.addAttribute("userEmail", userEmail);
        return "groups-create";
    }

    @PostMapping("/groups/new")
    public String saveGroup(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(name = "id", required = false) Long id, @Valid @ModelAttribute("group") GroupDto groupDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("group", groupDto);
            return "groups-create";
        }
        if(id != null){
            groupDto.setId(id);
        }
        else {groupDto.setId(1L);}
        Group group = groupService.saveGroup(groupDto);
        String userEmail = userDetails.getUsername();
        User user = userRepository.findUserByEmail(userEmail);
        GroupMemberPK groupMemberPK = new GroupMemberPK();
        if (group.getId() != null) {
            groupMemberPK.setGroupId(group.getId());
            groupMemberPK.setUserEmail(user.getEmail());
            GroupMember groupMember = new GroupMember();
            groupMember.setGroupMemberPK(groupMemberPK);
        }
        GroupMemberDto groupMemberDto = GroupMemberDto.builder()
                .groupMemberPK(groupMemberPK)
                .user(user)
                .group(group)
                .build();
        groupMemberService.saveGroupMember(groupMemberDto);
        return "redirect:/groups";
    }

    @GetMapping("/groups/names")
    @ResponseBody
    public List<GroupDto> getGroupsForUser(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        List<GroupDto> groups = groupMemberService.findGroupsByUserEmail(email);
        List<String> groupNames = groups.stream()
                .map(GroupDto::getName)
                .collect(Collectors.toList());


        return groups;
    }

    @GetMapping("/group/{groupId}")
    public String groupsEconomy(@AuthenticationPrincipal UserDetails userDetails, Model model, @PathVariable("groupId") Long groupId){

        //User user = userRepository.findUserByEmail(userDetails.getUsername());
        //List<Transaction> transactions = transactionRepository.
        //List<Group> groups = groupMemberRepository.findByUser(user);
        //model.addAttribute("groups", groups);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Group group = groupOptional.get();
        List<User> groupMemberList = groupMemberRepository.findByGroup(group);
        //List<String> groupMembeMail = groupMemberRepository.findByGroup(group);
        //List<User> groupMemberList = groupMemberRepository.findGroupMembersByGroup(group);
        /*for(String email : groupMembeMail){
            groupMemberList.add(userRepository.findUserByEmail(email));
        }*/



        List<Transaction> transactions = transactionRepository.findTopNByGroupOrderByCreatedOnDesc(groupId, 30);

        model.addAttribute("groupMemberlist", groupMemberList);
        model.addAttribute("userTransactions", transactions);
        model.addAttribute("group", group);

        return "groups";
    }

    @PostMapping("/joinGroup")
    public String joinGroup(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("groupId") Long groupId){

        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Group group = groupOptional.get();
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        GroupMemberPK groupMemberPK = new GroupMemberPK();
        if (group.getId() != null) {
            groupMemberPK.setGroupId(group.getId());
            groupMemberPK.setUserEmail(user.getEmail());
            GroupMember groupMember = new GroupMember();
            groupMember.setGroupMemberPK(groupMemberPK);
        }
        GroupMemberDto groupMemberDto = GroupMemberDto.builder()
                .groupMemberPK(groupMemberPK)
                .user(user)
                .group(group)
                .build();
        groupMemberService.saveGroupMember(groupMemberDto);

        return "redirect:/groups";
    }
}
