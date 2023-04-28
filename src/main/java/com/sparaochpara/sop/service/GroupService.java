package com.sparaochpara.sop.service;

import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.model.Group;

import java.util.List;

public interface GroupService {

    List<GroupDto> findAllGroups();
    GroupDto findGroupById(Long id);
    Group saveGroup(GroupDto groupDto);
}
