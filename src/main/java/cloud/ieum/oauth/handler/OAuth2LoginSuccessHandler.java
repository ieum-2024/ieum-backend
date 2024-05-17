package cloud.ieum.oauth.handler;

import cloud.ieum.jwt.JwtConstants;
import cloud.ieum.jwt.JwtUtils;
import cloud.ieum.user.PrincipalDetail;
import cloud.ieum.user.Role;
import cloud.ieum.user.User;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        /*PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();

        Map<String, Object> responseMap = principal.getMemberInfo();
        responseMap.put("accessToken", JwtUtils.generateToken(responseMap, JwtConstants.ACCESS_EXP_TIME));
        responseMap.put("refreshToken", JwtUtils.generateToken(responseMap, JwtConstants.REFRESH_EXP_TIME));

        Gson gson = new Gson();
        String json = gson.toJson(responseMap);

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(json);
        writer.flush();*/

        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        User member = principal.getUser();
        Map<String, Object> responseMap = principal.getMemberInfo();

        String accessToken = JwtUtils.generateToken(responseMap, JwtConstants.ACCESS_EXP_TIME);

        // 최초 로그인인 경우 추가 정보 입력을 위한 회원가입 페이지로 리다이렉트
        if (member.getRole().equals(Role.GUEST)) {
            response.addHeader(JwtConstants.ACCESS, JwtConstants.JWT_TYPE + accessToken);
            String redirectURL = UriComponentsBuilder.fromUriString("http://localhost:8080/user/info")
            //String redirectURL = UriComponentsBuilder.fromUriString("http://localhost:8080/signup")
                    .queryParam("socialId", member.getSocialId()).build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            getRedirectStrategy().sendRedirect(request, response, redirectURL);
        } else {
            String refreshToken = JwtUtils.generateToken(responseMap, JwtConstants.REFRESH_EXP_TIME);

            response.addHeader(JwtConstants.ACCESS, JwtConstants.JWT_TYPE + accessToken);
            response.addHeader(JwtConstants.REFRESH, JwtConstants.JWT_TYPE + refreshToken);

            // 최초 로그인이 아닌 경우 로그인 성공 페이지로 이동
            String redirectURL = UriComponentsBuilder.fromUriString("http://localhost:8080/interest/all")
            //String redirectURL = UriComponentsBuilder.fromUriString("http://localhost:8080/interest")
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            getRedirectStrategy().sendRedirect(request, response, redirectURL);
        }
    }
}
