package com.sparaochpara.sop.dto;

import com.sparaochpara.sop.model.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    private Long id;
    @NotEmpty(message = "Category must have a name")
    private String name;
    private User user;
}
