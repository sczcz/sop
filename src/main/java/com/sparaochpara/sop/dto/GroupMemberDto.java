package com.sparaochpara.sop.dto;

import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupMemberDto {
    private Group group;
    private User user;
}
