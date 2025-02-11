package org.blogapplication.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.blogapplication.entities.User;

@Data
@AllArgsConstructor
public class JwtAuthResponse {

    private String accessToken;
    private String refreshToken;
    User user;
}
