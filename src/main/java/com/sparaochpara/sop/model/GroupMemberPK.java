package com.sparaochpara.sop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GroupMemberPK implements Serializable {
    private Long groupId;
    private String userEmail;
}
