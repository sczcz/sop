package com.sparaochpara.sop.repository;

import com.sparaochpara.sop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);
}
