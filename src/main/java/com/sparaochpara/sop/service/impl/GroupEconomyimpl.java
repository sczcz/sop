package com.sparaochpara.sop.service.impl;

import com.sparaochpara.sop.dto.GroupEconomyDto;
import com.sparaochpara.sop.model.GroupEconomy;
import com.sparaochpara.sop.repository.GroupEconomyRepository;
import com.sparaochpara.sop.service.GroupEconomyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class GroupEconomyimpl implements GroupEconomyService {
    private GroupEconomyRepository groupEconomyRepository;
    public GroupEconomyimpl(GroupEconomyRepository groupEconomyRepository) {
        this.groupEconomyRepository = groupEconomyRepository;
    }
    @Override
    public List<GroupEconomyDto> findAllGroups() {
        List<GroupEconomy> groupEconomies = groupEconomyRepository.findAll();
        return groupEconomies.stream().map((groupEconomy) -> mapToGroupEconomyDto(groupEconomy)).collect(Collectors.toList());
    }
    private GroupEconomyDto mapToGroupEconomyDto(GroupEconomy groupEconomy) {
        GroupEconomyDto groupEconomyDto = GroupEconomyDto.builder()
                .id(groupEconomy.getId())
                .name(groupEconomy.getName())
                .sum(groupEconomy.getSum())
                .category(groupEconomy.getCategory())
                .createdOn(groupEconomy.getCreatedOn())
                .updatedOn(groupEconomy.getUpdatedOn())
                .isIncome(groupEconomy.isIncome())
                .user(groupEconomy.getUser())
                .build();
        return groupEconomyDto;
    }
}
