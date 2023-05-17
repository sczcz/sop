package com.sparaochpara.sop.dto;

import com.sparaochpara.sop.model.Category;
import com.sparaochpara.sop.model.Group;
import com.sparaochpara.sop.model.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDto {

    private Long id;
    private String description;
   // @NotEmpty(message = "Skriv in en summa")
    private double amount;
    private Category category;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private User user;
    private Group group;
}
