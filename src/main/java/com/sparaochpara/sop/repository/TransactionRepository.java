package com.sparaochpara.sop.repository;

import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.Transaction;
import com.sparaochpara.sop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findById(Long id);

    @Query("SELECT t FROM Transaction t WHERE t.user.email = :email")
    List<Transaction> findByUserEmail(@Param("email") String email);

    @Query("SELECT t FROM Transaction t WHERE t.user.email = ?1 ORDER BY t.createdOn DESC")
    List<Transaction> findTopNByUserEmailOrderByCreatedOnDesc(String userEmail, int limit);
}
