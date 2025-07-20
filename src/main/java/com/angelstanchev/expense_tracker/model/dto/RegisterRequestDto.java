package com.angelstanchev.expense_tracker.model.dto;

import com.angelstanchev.expense_tracker.model.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@FieldMatch(first = "password", second = "confirmPassword", message = "Passwords do not match")
public record RegisterRequestDto(

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 5, max = 20)
        String firstname,

        @NotBlank
        @Size(min = 5, max = 20)
        String lastname,

        @NotBlank
        @Size(min = 8)
        String password,

        @NotBlank
        @Size(min = 8)
        String confirmPassword,

        @NotBlank
        List<UserRoleDto> userRole
) {
}
