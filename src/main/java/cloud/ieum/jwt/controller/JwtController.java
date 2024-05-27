package cloud.ieum.jwt.controller;
import cloud.ieum.jwt.JwtConstants;
import cloud.ieum.jwt.JwtUtils;
import cloud.ieum.jwt.tokenDTO;
import com.amazonaws.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtUtils jwtUtils;

    @PostMapping("/user/reissue")
    public ResponseEntity<tokenDTO> refresh(@RequestHeader("Authorization") String authHeader, String refreshToken) {
        log.info("Refresh Token = {}", refreshToken);
        if (authHeader == null) {
            throw new RuntimeException("Access Token 이 존재하지 않습니다");
        } else if (!authHeader.startsWith(JwtConstants.JWT_TYPE)) {
            throw new RuntimeException("BEARER 로 시작하지 않는 올바르지 않은 토큰 형식입니다");
        }

        String accessToken = jwtUtils.getTokenFromHeader(authHeader);

        // Access Token 의 만료 여부 확인
        if (!jwtUtils.isExpired(accessToken)) {
            return ResponseEntity.ok(new tokenDTO(accessToken, refreshToken));
        }

        // refreshToken 검증 후 새로운 토큰 생성 후 전달
        Map<String, Object> claims = jwtUtils.validateToken(refreshToken);
        String newAccessToken = jwtUtils.generateToken(claims, JwtConstants.ACCESS_EXP_TIME);

        String newRefreshToken = refreshToken;
        long expTime = jwtUtils.tokenRemainTime((Integer) claims.get("exp"));   // Refresh Token 남은 만료 시간
        log.info("Refresh Token Remain Expire Time = {}", expTime);
        // Refresh Token 의 만료 시간이 한 시간도 남지 않은 경우
        if (expTime <= 60) {
            newRefreshToken = jwtUtils.generateToken(claims, JwtConstants.REFRESH_EXP_TIME);
        }
        tokenDTO tokenDTO = new tokenDTO(JwtConstants.JWT_TYPE + newAccessToken, newRefreshToken);

        return ResponseEntity.ok(tokenDTO);
    }
}