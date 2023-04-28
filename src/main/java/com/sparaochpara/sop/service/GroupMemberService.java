package com.sparaochpara.sop.service;

import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.dto.GroupMemberDto;
import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.GroupMember;
import com.sparaochpara.sop.model.User;

import java.util.List;

public interface GroupMemberService {
    List<GroupDto> findGroupsByUser(User user);
    List<UserDto> findUsersByGroup(Group group);
    GroupMember saveGroupMember(GroupMemberDto groupMemberDto);

    List<GroupDto> findGroupsByUserEmail(String email);

}
