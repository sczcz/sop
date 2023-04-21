package com.sparaochpara.sop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"group_member\"")
@IdClass(GroupMemberPK.class)
public class GroupMember {

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;
    @Id
    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User user;


}
