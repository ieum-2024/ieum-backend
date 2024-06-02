package cloud.ieum.jwt.filter;

import cloud.ieum.jwt.JwtConstants;
import cloud.ieum.jwt.JwtUtils;
import cloud.ieum.utils.ApiUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {
    private static final String[] whitelist = {"/user/reissue", "/user/login/kakao", "/user/login", "/login/kakao", "/login", "/user"};
    private final JwtUtils jwtUtils;
    private final ObjectMapper om = new ObjectMapper();

    private static void checkAuthorizationHeader(String header) {
        if(header == null) {
            throw new RuntimeException("토큰이 전달되지 않았습니다");
        } else if (!header.startsWith(JwtConstants.JWT_TYPE)) {
            throw new RuntimeException("BEARER 로 시작하지 않는 올바르지 않은 토큰 형식입니다");
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();

        //if (requestURI.startsWith("/content")) return false; // "/content" 필터 걸리도록 (임시 테스트용)
        return Arrays.stream(whitelist).anyMatch(requestURI::startsWith);

    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(JwtConstants.JWT_HEADER);

        log.info("필터중...");
        log.info(authHeader);

        try {
            checkAuthorizationHeader(authHeader);   // header 가 올바른 형식인지 체크
            String token = jwtUtils.getTokenFromHeader(authHeader);
            log.info("authentication = {}", token);

            Authentication authentication = jwtUtils.getAuthentication(token);

            log.info("authentication = {}", authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);    // 다음 필터로 이동
        } catch (Exception e) {
            ApiUtils.ApiResult<?> exception = ApiUtils.error("리프레시 에러가 발생했습니다.", HttpStatus.UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(exception.getError().getStatus());
            response.getOutputStream().write(om.writeValueAsBytes(exception));
        }
    }

}
