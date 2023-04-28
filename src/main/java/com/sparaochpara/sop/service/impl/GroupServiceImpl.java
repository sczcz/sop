package com.sparaochpara.sop.service.impl;

import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.User;
import com.sparaochpara.sop.repository.GroupRepository;
import com.sparaochpara.sop.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class GroupServiceImpl implements GroupService{

    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository){
        this.groupRepository=groupRepository;
    }
    @Override
    public List<GroupDto> findAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map((group) -> mapToGroupDto(group)).collect(Collectors.toList());
    }
    @Override
    public GroupDto findGroupById(Long id) {
        Group group = groupRepository.findById(id).get();
        return mapToGroupDto(group);
    }

    @Override
    public Group saveGroup(GroupDto groupDto) {
        Group group = mapToGroup(groupDto);
        return groupRepository.save(group);
    }

    private GroupDto mapToGroupDto(Group group){
        GroupDto groupDto = GroupDto.builder()
                .id(group.getId())
                .createdOn(group.getCreatedOn())
                .name(group.getName())
                .updatedOn(group.getUpdatedOn())
                .build();
        return groupDto;
    }

    private Group mapToGroup(GroupDto groupDto){
        Group group = Group.builder()
                .id(groupDto.getId())
                .createdOn(groupDto.getCreatedOn())
                .name(groupDto.getName())
                .updatedOn(groupDto.getUpdatedOn())
                .build();
        return group;
    }
}
