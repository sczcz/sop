package com.sparaochpara.sop.service.impl;

import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.dto.GroupMemberDto;
import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.GroupMember;
import com.sparaochpara.sop.model.User;
import com.sparaochpara.sop.repository.GroupMemberRepository;
import com.sparaochpara.sop.service.GroupMemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {
    private GroupMemberRepository groupMemberRepository;
    public GroupMemberServiceImpl(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }
    @Override
    public List<GroupDto> findGroupsByUser(User user) {
        List<Group> groups = groupMemberRepository.findByUser(user).get();
        return groups.stream().map((group) -> mapToGroupDto(group)).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findUsersByGroup(Group group) {
        List<User> users = groupMemberRepository.findByGroup(group).get();
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public GroupMember saveGroupMember(GroupMemberDto groupMemberDto) {
        GroupMember groupMember = mapToGroupMember(groupMemberDto);
        return groupMemberRepository.save(groupMember);
    }

    private GroupDto mapToGroupDto(Group group){
        return GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .createdOn(group.getCreatedOn())
                .updatedOn(group.getUpdatedOn())
                .build();
    }

    private UserDto mapToUserDto(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdOn(user.getCreatedOn())
                .updatedOn(user.getUpdatedOn())
                .build();
    }

    private GroupMember mapToGroupMember(GroupMemberDto groupMemberDto) {
        return GroupMember.builder()
                .group(groupMemberDto.getGroup())
                .user(groupMemberDto.getUser())
                .build();
    }

    private GroupMemberDto mapToGroupMemberDto(GroupMember groupMember) {
        return GroupMemberDto.builder()
                .group(groupMember.getGroup())
                .user(groupMember.getUser())
                .build();
    }
}
