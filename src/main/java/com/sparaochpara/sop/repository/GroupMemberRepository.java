package com.sparaochpara.sop.repository;

import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.GroupMember;
import com.sparaochpara.sop.model.GroupMemberPK;
import com.sparaochpara.sop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberPK> {
    Optional<GroupMember> findById(GroupMemberPK groupMemberPK);
    @Query("SELECT gm.group FROM GroupMember gm JOIN gm.group g WHERE gm.user = :user")
    List<Group> findByUser(@Param("user") User user);
    Optional<List<User>> findByGroup(Group group);
    List<Group> findByUserEmail(String userEmail);
}
