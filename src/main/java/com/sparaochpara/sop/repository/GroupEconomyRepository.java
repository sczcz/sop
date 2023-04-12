package com.sparaochpara.sop.repository;

import com.sparaochpara.sop.model.GroupEconomy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupEconomyRepository extends JpaRepository<GroupEconomy, Long> {
    Optional<GroupEconomy> findGroupEconomyById(String url);
}
