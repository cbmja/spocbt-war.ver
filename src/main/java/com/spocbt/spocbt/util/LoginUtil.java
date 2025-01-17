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

    public LoginDto getKakaoToken(String code){

        // token 요청 url
        String kakaoTokenUrl = "https://kauth.kakao.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();
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
            RestTemplate restTemplate = new RestTemplate();
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
            RestTemplate restTemplate = new RestTemplate();
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

            kid = new ObjectMapper().readTree(headerJson).get("kid").asText();

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

    public static PublicKey getPublicKey(String kid) {
        try {
            URL url = new URL("https://kauth.kakao.com/.well-known/jwks.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jwks = mapper.readTree(conn.getInputStream()).get("keys");

            for (JsonNode key : jwks) {
                if (key.get("kid").asText().equals(kid)) {
                    // n과 e를 Base64 URL 디코딩 후 BigInteger로 변환
                    BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(key.get("n").asText()));
                    BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(key.get("e").asText()));

                    // RSAPublicKeySpec을 사용해 공개 키 생성
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    return keyFactory.generatePublic(new RSAPublicKeySpec(modulus, exponent));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public String decrypt(String encryptedText) {
        String result = "";
        try {
            // SecretKeySpec 생성 (암호화에 사용한 키를 동일하게 사용)
            SecretKeySpec keySpec = new SecretKeySpec(default_secret.getBytes(), "AES");

            // Cipher 객체 생성 및 초기화
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            // Base64 디코딩 후 복호화
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);

            // 결과 문자열 변환
            result = new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public String encrypt(String plainText){
        String result = "";
        try{
            SecretKeySpec keySpec = new SecretKeySpec(default_secret.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

            result = Base64.getEncoder().encodeToString(encryptedBytes);
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
