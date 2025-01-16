package com.spocbt.spocbt.util;

import lombok.Data;

@Data
public class LoginDto {

    private String tokenType; // kakao , naver
    private String accessToken; // kakao , naver
    private String idToken; // kakao ,
    private int expiresIn; // kakao , naver
    private String refreshToken; // kakao , naver
    private int refreshTokenExpiresIn; // kakao ,
    private String scope; // kakao ,
    private String loginId; // kakao , naver
    private String appKey; // kakao ,
    private String error; // naver ,
    private String errorDescription; // naver ,


}
