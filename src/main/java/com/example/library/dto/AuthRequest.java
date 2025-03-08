package com.example.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @Email(message = "Email düzgün formatda olmalıdır")
    @NotBlank(message = "Email boş ola bilməz")
    private String email;

    @NotBlank(message = "Şifrə boş ola bilməz")
    private String password;
}
