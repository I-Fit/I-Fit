package kr.co.ifit.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDtoRes {
    private String message;
    private String token;
    private Long userId;
}
