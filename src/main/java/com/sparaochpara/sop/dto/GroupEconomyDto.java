package com.sparaochpara.sop.dto;

import com.sparaochpara.sop.model.Category;
import com.sparaochpara.sop.model.User;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class GroupEconomyDto {

    private Long id;
    private String name;
    private double sum;
    private Category category;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private boolean isIncome;
    private User user;
}
