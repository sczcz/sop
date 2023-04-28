package com.sparaochpara.sop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GroupDto {

    @NotEmpty(message = "Group must have a name")
    private String name;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
