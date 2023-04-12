package com.sparaochpara.sop.dto;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {

    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
