package com.baska.UserService.Services;

import com.baska.UserService.Payloads.TokenResponse;
import com.baska.UserService.Repository.RefreshTokensRepository;
import com.baska.UserService.Repository.TokenRepository;
import com.baska.UserService.Repository.UserRepository;
import com.baska.UserService.models.RefreshTokens;
import com.baska.UserService.models.TokenData;
import com.baska.UserService.models.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.*;

@Service
public class JWTService {


    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SRandomService sRandomService;

    @Autowired
    RefreshTokensRepository refreshTokensRepository;

    @Autowired
    UserServiceImpl userServiceImpl;


    public Map<Integer, String> getNewToken(User user){
        Date now = Date.from(Instant.now());
        String salt = sRandomService.getSalt(15,30);
        Date expireDate = Date.from(Instant.now().plusSeconds(60*5));
        String token = Jwts.builder()
                .setSubject(salt)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, user.getSalt())
                .compact();
        TokenData tokenData = new TokenData();
        tokenData.setTokenSecret(salt);
        tokenData.setUserId(user.getId());
        tokenData.setExpiredTime(expireDate.toInstant());
        tokenRepository.save(tokenData);
        Map<Integer,String> response = new HashMap<Integer, String>();
        response.put(1,token);
        response.put(2,tokenData.getId().toString());
        return response;
    }


    public Map<Integer,String> getNewRefreshToken(User user){
        Map<Integer,String> tokenResponse = getNewToken(user);
        RefreshTokens refreshTokens = new RefreshTokens();
        refreshTokens.setUserId(user.getId());
        refreshTokens.setTokenId(Long.valueOf(tokenResponse.get(2)));
        String secretKey = "";
        while (true) {
            secretKey = sRandomService.getSalt(15, 30);
            if (!refreshTokensRepository.existsByUserIdAndRefreshTokenSecret(user.getId(), secretKey)) {
                break;
            }
        }
        refreshTokens.setRefreshTokenSecret(secretKey);
        refreshTokensRepository.save(refreshTokens);
        String passtoken = userServiceImpl.getSecureHash(secretKey);
        tokenResponse.replace(2,passtoken);
        return tokenResponse;
    }


    public Boolean checkJwtToken(String token, User user) {
        List<String> userIdList = tokenRepository.findByUserId(user.getId());
        String secretKey = Jwts.parser().setSigningKey(user.getSalt()).parseClaimsJws(token).getBody().getSubject();
        boolean finded = false;
        for (String element: userIdList){
            if (element.equals(secretKey)){
                finded = true;
                break;
            }
        }
        return finded;
    }


    public Boolean checkRefreshJwtToken(String token, User user) {
        List<RefreshTokens> refreshTokensList = refreshTokensRepository.findByUserId(user.getId());
        boolean finded = false;
        for (RefreshTokens element: refreshTokensList){
            if (userServiceImpl.getSecureHash(element.getRefreshTokenSecret()).equals(token)){
                TokenData tokenData = tokenRepository.findById(element.getTokenId()).orElse(new TokenData());
                refreshTokensRepository.delete(element);
                tokenRepository.delete(tokenData);
                finded = true;
                break;
            }
        }
        return finded;
    }

    public TokenResponse validateJwtToken(String authToken,User user) {

        TokenResponse response = new TokenResponse(false,false);
        try {
            Jwts.parser().setSigningKey(user.getSalt()).parseClaimsJws(authToken);
            response.setValidate(true);
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            response.setExpired(true);
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return response;
    }

    public String getJwt(String request) {
        if (StringUtils.hasText(request) && request.startsWith("Bearer ")) {
            return request.substring(7, request.length());
        }
        return null;
    }

}
