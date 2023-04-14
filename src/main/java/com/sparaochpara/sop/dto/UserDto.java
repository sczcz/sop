package com.sparaochpara.sop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {

    //@NotEmpty(message = "Enter an email")
    @Email(message = "Enter valid email")
    private String email;
    @NotEmpty(message = "Enter name")
    private String firstName;
    @NotEmpty(message = "Enter lastname")
    private String lastName;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    @NotEmpty(message = "Enter password")
    private String password;

}
