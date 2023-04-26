package com.sparaochpara.sop.service;

import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.dto.UserDto;

import java.util.List;

public interface GroupService {

    List<GroupDto> findAllGroups();
    GroupDto findGroupById(Long id);
}
