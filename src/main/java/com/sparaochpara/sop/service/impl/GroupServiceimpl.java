package com.sparaochpara.sop.service.impl;

import com.sparaochpara.sop.dto.GroupDto;
import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.repository.GroupRepository;
import com.sparaochpara.sop.service.GroupService;

import java.util.List;
import java.util.stream.Collectors;

public class GroupServiceimpl implements  GroupService{

    private GroupRepository groupRepository;

    public GroupServiceimpl(GroupRepository groupRepository){
        this.groupRepository=groupRepository;
    }
    @Override
    public List<GroupDto> findAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map((group) -> mapToGroupDto(group)).collect(Collectors.toList());
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
}
