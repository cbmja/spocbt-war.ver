package com.spocbt.spocbt.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Component
public class LoginUtil {


    @Value("${kakao.nativeAppKey}")
    private String kakao_nativeAppKey;
    @Value("${kakao.restApiKey}")
    private String kakao_restApiKey;
    @Value("${kakao.javaScriptKey}")
    private String kakao_javaScriptKey;
    @Value("${kakao.adminKey}")
    private String kakao_adminKey;
    @Value("${kakao.clientSecret}")
    private String kakao_clientSecret;
    @Value("${kakao.redirectUri}")
    private String kakao_redirectUri;
    @Value("${hash.default_secret}")
    private String default_secret;

    @Value("${naver.clientId}")
    private String naver_clientId;
    @Value("${naver.clientSecret}")
    private String naver_clientSecret;
    @Value("${naver.redirectUri}")
    private String naver_redirectUri;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RestTemplate restTemplate = new RestTemplate();

    public LoginDto getKakaoToken(String code){

        // token 요청 url
        String kakaoTokenUrl = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();

        // 요청 헤더, 바디
        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8"); // 필수 'application/x-www-form-urlencoded;charset=utf-8' 고정
        String body = "grant_type=authorization_code" // 필수 'authorization_code' 고정
                + "&client_id=" +  kakao_nativeAppKey  // 필수
                + "&redirect_uri=" + kakao_redirectUri // 필수
                + "&code=" + code // 필수
                + "&client_secret=" + kakao_clientSecret; // 선택 : [내 애플리케이션] > [카카오 로그인] > [보안]

        ResponseEntity<String> response = null;
        LoginDto dto = new LoginDto();

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);


            // token 요청
            response = restTemplate.exchange(
                    kakaoTokenUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            // 응답 수집
            JSONObject jsonResponse = new JSONObject(response.getBody());
            dto.setTokenType(jsonResponse.getString("token_type"));
            dto.setAccessToken(jsonResponse.getString("access_token"));
            dto.setRefreshToken(jsonResponse.getString("refresh_token"));
            dto.setExpiresIn(jsonResponse.getInt("expires_in"));
            dto.setIdToken(jsonResponse.getString("id_token"));
            dto.setRefreshTokenExpiresIn(jsonResponse.getInt("refresh_token_expires_in"));
            //dto.setScope(jsonResponse.getString("scope"));

            return dto;

    }

    public LoginDto getNaverToken(String code){
            // token 요청 url
            String kakaoTokenUrl = "https://nid.naver.com/oauth2.0/token";

            HttpHeaders headers = new HttpHeaders();

            // 요청 헤더, 바디
            headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8"); // 필수 'application/x-www-form-urlencoded;charset=utf-8' 고정
            String body = "grant_type=authorization_code" // 필수 'authorization_code' 고정
                    + "&client_id=" +  naver_clientId  // 필수
                    + "&client_secret=" + naver_clientSecret // 필수
                    + "&code=" + code // 필수
                    + "&state=1234"; // 선택 : [내 애플리케이션] > [카카오 로그인] > [보안]

            ResponseEntity<String> response = null;
            LoginDto dto = new LoginDto();

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);


            // token 요청
            response = restTemplate.exchange(
                    kakaoTokenUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );



            // 응답 수집
            JSONObject jsonResponse = new JSONObject(response.getBody());

            dto.setTokenType(jsonResponse.getString("token_type"));
            dto.setAccessToken(jsonResponse.getString("access_token"));
            dto.setRefreshToken(jsonResponse.getString("refresh_token"));
            dto.setExpiresIn(jsonResponse.getInt("expires_in"));

            return dto;


    }

    public String getNaverProfile(String accessToken){
            // token 요청 url
            String kakaoTokenUrl = "https://openapi.naver.com/v1/nid/me";

            HttpHeaders headers = new HttpHeaders();

            // 요청 헤더, 바디
            headers.set("Authorization","Bearer "+accessToken); // 필수 'application/x-www-form-urlencoded;charset=utf-8' 고정


            ResponseEntity<String> response = null;

            HttpEntity<String> requestEntity = new HttpEntity<>(headers);


            // token 요청
            response = restTemplate.exchange(
                    kakaoTokenUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // 응답 수집
            JSONObject jsonResponse = new JSONObject(response.getBody());

            if(!jsonResponse.getString("message").equals("success")){
                return "err";
            }else {
                return jsonResponse.getJSONObject("response").getString("id");
            }
    }


    public String verifyToken(String idToken, LoginDto dto) {

        try {
            String[] parts = idToken.split("\\.");
            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            String kid = "";

            kid = objectMapper.readTree(headerJson).get("kid").asText();
            PublicKey publicKey = getPublicKey(kid);

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(idToken)
                    .getBody();
            String nonce = claims.get("nonce", String.class);
            String _nonce = "1234";
            if(!nonce.equals(_nonce)){
                return "nonce";
            }

            dto.setLoginId(claims.get("sub", String.class));
            dto.setAppKey(claims.get("aud", String.class));

            return "success";
        } catch (Exception e) {
            e.printStackTrace();

            return "err";
        }

    }

    public PublicKey getPublicKey(String kid) {
        try {
            URL url = new URL("https://kauth.kakao.com/.well-known/jwks.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (InputStream inputStream = conn.getInputStream()) {
                JsonNode jwks = objectMapper.readTree(inputStream).get("keys");

                for (JsonNode key : jwks) {
                    if (key.get("kid").asText().equals(kid)) {
                        BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(key.get("n").asText()));
                        BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(key.get("e").asText()));
                        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                        return keyFactory.generatePublic(new RSAPublicKeySpec(modulus, exponent));
                    }
                }
            } finally {
                conn.disconnect(); // 연결 해제
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    private final ThreadLocal<Cipher> DECRYPT_CIPHER = ThreadLocal.withInitial(() -> {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(default_secret.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return cipher;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });

    private final ThreadLocal<Cipher> ENCRYPT_CIPHER = ThreadLocal.withInitial(() -> {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(default_secret.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return cipher;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });

    // decrypt 메서드
    public String decrypt(String encryptedText) {
        try {
            // Base64 디코딩 및 Cipher 변환을 스트림으로 처리
            return new String(
                    DECRYPT_CIPHER.get().doFinal(
                            Base64.getDecoder().decode(encryptedText)
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // encrypt 메서드
    public String encrypt(String plainText) {
        try {
            // Cipher 변환 및 Base64 인코딩을 스트림으로 처리
            return Base64.getEncoder().encodeToString(
                    ENCRYPT_CIPHER.get().doFinal(plainText.getBytes())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



}
