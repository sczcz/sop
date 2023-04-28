package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.dto.GroupMemberDto;
import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.User;
import com.sparaochpara.sop.repository.UserRepository;
import com.sparaochpara.sop.service.GroupMemberService;
import com.sparaochpara.sop.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class GroupController {
    private GroupService groupService;
    private GroupMemberService groupMemberService;
    private UserRepository userRepository;

    @Autowired
    public GroupController (GroupService groupService, GroupMemberService groupMemberService, UserRepository userRepository) {
        this.groupService = groupService;
        this.groupMemberService = groupMemberService;
        this.userRepository = userRepository;
    }
    @GetMapping ("{userEmail}/groups/{groupId}")
    public String groupDetail(@PathVariable("groupId") Long groupId, Model model) {
        GroupDto groupDto = groupService.findGroupById(groupId);
        model.addAttribute ("group", groupDto);
        return "groups-detail";
    }
    @GetMapping ("/{userEmail}/groups")
    public String groupList(@PathVariable("userEmail") String userEmail, Model model) {
        List<GroupDto> groups = groupMemberService.findGroupsByUserEmail(userEmail);
        model.addAttribute("groups", groups);
        return "groups-list";
    }

    @GetMapping("{userEmail}/groups/new")
    public String createGroupForm(@PathVariable("userEmail") String userEmail, Model model){
        Group group = new Group();
        model.addAttribute("group", group);
        model.addAttribute("userEmail", userEmail);
        return "groups-create";
    }

    @PostMapping("{userEmail}/groups/new")
    public String saveGroup(@PathVariable("userEmail") String userEmail, @RequestParam("id") Long id, @Valid @ModelAttribute("group") GroupDto groupDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("group", groupDto);
            return "groups-create";
        }
        groupDto.setId(id);
        Group group = groupService.saveGroup(groupDto);
        User user = userRepository.findUserByEmail(userEmail);
        GroupMemberDto groupMemberDto = GroupMemberDto.builder()
                .user(user)
                .group(group)
                .build();
        groupMemberService.saveGroupMember(groupMemberDto);
        return "redirect:{userEmail}/groups";
    }
}
