package com.sparaochpara.sop.dto;

import com.sparaochpara.sop.model.Category;
import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.User;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDto {

    private Long id;
    private String description;
    private double amount;
    private Category category;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private User user;
    private Group group;
}
