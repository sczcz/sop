package com.sparaochpara.sop.repository;

import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.GroupMember;
import com.sparaochpara.sop.model.GroupMemberPK;
import com.sparaochpara.sop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberPK> {
    Optional<GroupMember> findById(GroupMemberPK groupMemberPK);
    Optional<List<Group>> findByUser(String email);
    Optional<List<User>> findByGroup(Long id);
}
