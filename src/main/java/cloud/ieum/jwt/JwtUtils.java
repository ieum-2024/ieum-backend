package cloud.ieum.jwt;

import cloud.ieum.user.PrincipalDetail;
import cloud.ieum.user.Role;
import cloud.ieum.user.User;
import cloud.ieum.user.service.PrincipalDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtUtils {
    public static String secretKey = JwtConstants.key;
    private final PrincipalDetailsService principalDetailsService;

    // 헤더에 "Bearer XXX" 형식으로 담겨온 토큰을 추출한다
    public String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    public String generateToken(Map<String, Object> valueMap, int validTime) {
        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(JwtUtils.secretKey.getBytes(StandardCharsets.UTF_8));
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return Jwts.builder()
                .setHeader(Map.of("typ","JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(validTime).toInstant()))
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Map<String, Object> claims = validateToken(token);
        log.info("get authentication");

        String name = (String) claims.get("name");
        String role = (String) claims.get("role");
        Role memberRole = Role.valueOf(role);
        UserDetails userDetails = principalDetailsService.loadUserByUsername(name);

        User member = User.builder().name(name).role(memberRole).build();
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(member.getRole().getValue()));
        PrincipalDetail principalDetail = new PrincipalDetail(member, authorities);

        //return new UsernamePasswordAuthenticationToken(principalDetail, "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }



    public Map<String, Object> validateToken(String token) {
        Map<String, Object> claim = null;

        SecretKey key = Keys.hmacShaKeyFor(JwtUtils.secretKey.getBytes(StandardCharsets.UTF_8));
        claim = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
                .getBody();

        return claim;
    }

    public boolean isExpired(String token) {
        try {
            validateToken(token);
        } catch (Exception e) {
            return (e instanceof ExpiredJwtException);
        }
        return false;
    }

    public long tokenRemainTime(Long expTime) {
        Date expDate = new Date(expTime * (1000));
        long remainMs = expDate.getTime() - System.currentTimeMillis();
        return remainMs / (1000 * 60);
    }


}
