package org.blogapplication.payloads;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
