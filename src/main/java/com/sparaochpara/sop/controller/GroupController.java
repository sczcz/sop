package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.model.User;
import com.sparaochpara.sop.service.GroupMemberService;
import com.sparaochpara.sop.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller

public class GroupController {
    private GroupService groupService;
    private GroupMemberService groupMemberService;

    @Autowired
    public GroupController (GroupService groupService, GroupMemberService groupMemberService) {
        this.groupService = groupService;
        this.groupMemberService = groupMemberService;
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
}
