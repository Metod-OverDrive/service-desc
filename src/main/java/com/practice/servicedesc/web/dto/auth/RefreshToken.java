package com.practice.servicedesc.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshToken {

    @NotNull(message = "Refresh token must be not null")
    private String refreshToken;

}
