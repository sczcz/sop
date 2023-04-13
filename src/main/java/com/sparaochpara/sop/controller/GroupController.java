package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.GroupEconomyDto;
import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.service.GroupEconomyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GroupController {
    private GroupEconomyService groupEconomyService;

    @Autowired
    public GroupController(GroupEconomyService groupEconomyService){this.groupEconomyService = groupEconomyService;}

    @GetMapping("/groups")
    public String listUsers(Model model){
        List<GroupEconomyDto> groups = groupEconomyService.findAllGroups();
        model.addAttribute("groups", groups);
        return "groups-list";
    }
}
