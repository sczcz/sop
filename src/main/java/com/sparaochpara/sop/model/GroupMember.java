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
public class GroupMember {
    @EmbeddedId
    private GroupMemberPK groupMemberPK;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @ManyToOne
    @MapsId("userEmail")
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User user;
}
