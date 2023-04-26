package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class GroupController {
    private GroupService groupService;

    @Autowired
    public GroupController (GroupService groupService) {
        this.groupService = groupService;
    }
    @GetMapping ("/groups/{groupId}")
    public String groupDetail(@PathVariable("groupId") long groupId, Model model) {
        GroupDto groupDto = groupService.findGroupById(groupId);
        model.addAttribute ("group", groupDto);
        return "groups-detail";
    }
}
