package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.dto.GroupMemberDto;
import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.GroupMember;
import com.sparaochpara.sop.model.GroupMemberPK;
import com.sparaochpara.sop.model.User;
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
    @GetMapping ("/groups")
    public String groupList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String userEmail = userDetails.getUsername();
        List<GroupDto> groups = groupMemberService.findGroupsByUserEmail(userEmail);
        String firstName = userRepository.findUserByEmail(userEmail).getFirstName();
        model.addAttribute("groups", groups);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("firstName", firstName);
        return "groups-list";
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
}
