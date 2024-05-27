package cloud.ieum.oauth.service;

import cloud.ieum.jwt.JwtConstants;
import cloud.ieum.jwt.JwtUtils;
import cloud.ieum.oauth.DTO.KakaoUserInfo;
import cloud.ieum.oauth.DTO.LoginDTO;
import cloud.ieum.oauth.DTO.SessionUser;
import cloud.ieum.user.Role;
import cloud.ieum.user.User;
import cloud.ieum.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    private final JwtUtils jwtUtils;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String REST_API_KEY;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String AUTHORIZE_URI;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    public String TOKEN_URI;

    public String KAKAO_API_HOST = "https://kapi.kakao.com";
    @Transactional
    public LoginDTO login(String code) { //로그인 로직 모두 처리하는 메서드
        log.info(code);
        //kakao로부터 access, refresh토큰 받아옴
        WebClient webClient = WebClient.builder()
                .baseUrl(TOKEN_URI)
                .build();

        JSONObject tokenResponse = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", REST_API_KEY)
                        .queryParam("redirect_uri", REDIRECT_URI)
                        .queryParam("code", code).build())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .retrieve().bodyToMono(JSONObject.class).block();

        log.info(tokenResponse.toString());
        User user = getUserProfile(tokenResponse.getAsString("access_token"));

        return new LoginDTO(jwtUtils.generateToken(tokenResponse, JwtConstants.ACCESS_EXP_TIME), jwtUtils.generateToken(tokenResponse, JwtConstants.REFRESH_EXP_TIME), user.getId());

    }

    private Map<String, Object> getUserAttributes(String accessToken) {
        return WebClient.create()
                .get()
                .uri(KAKAO_API_HOST + "/v2/user/me")
                .headers(header -> header.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private User getUserProfile(String accessToken) {
        Map<String, Object> userAttributes = getUserAttributes(accessToken);
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(userAttributes);
        String socialID  = kakaoUserInfo.getSocialId();
        String name = kakaoUserInfo.getName();
        Optional<User> bySocialId = userRepository.findBySocialId(socialID);
        User member = bySocialId.orElseGet(() -> saveSocialMember(socialID, name));
        httpSession.setAttribute("loginUser", new SessionUser(member));
        log.info(httpSession.getAttribute("loginUser").toString());

        return member;
    }

    public User saveSocialMember(String socialId, String name) {
        User newMember = User.builder().socialId(socialId).name(name).role(Role.GUEST).build();
        return userRepository.save(newMember);
    }

}