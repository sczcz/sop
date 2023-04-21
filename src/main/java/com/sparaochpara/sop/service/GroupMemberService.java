package com.sparaochpara.sop.service;

import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.dto.GroupMemberDto;
import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.model.GroupMember;

import java.util.List;

public interface GroupMemberService {
    List<GroupDto> findGroupsByUser(String email);
    List<UserDto> findUsersByGroup(Long groupId);
    GroupMember saveGroupMember(GroupMemberDto groupMemberDto);

}
