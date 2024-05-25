package cloud.ieum.oauth.service;

import cloud.ieum.oauth.KakaoUserInfo;
import cloud.ieum.oauth.LoginDTO;
import cloud.ieum.user.Role;
import cloud.ieum.user.User;
import cloud.ieum.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Autowired
    public HttpCallService httpCallService;

    private String REST_API_KEY = "0079af8e66a2f897721d5419a0933b70";
    private String CLIENT_SECRET = "zswRxlhG4BxDtwdibpuOIRyw6b75yekK";


    private String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/kakao";


    private String AUTHORIZE_URI = "https://kauth.kakao.com/oauth/authorize";

    public String TOKEN_URI = "https://kauth.kakao.com/oauth/token";

    public String KAKAO_API_HOST = "https://kapi.kakao.com";
    @Transactional
    public LoginDTO login(String code) { //로그인 로직 모두 처리하는 메서드
        /*ClientRegistration provider = inMemoryRepository.findByRegistrationId(JwtConstants.PROVIDER);
        log.info(code);
        //kakao로부터 access, refresh토큰 받아옴
        OAuth2AccessTokenResponse tokenResponse = getToken(code, provider);
        log.info(tokenResponse.getAccessToken().toString());
        log.info(tokenResponse.getRefreshToken().toString());
        //kakao로부터 유저정보 받아서 db에 저장
        User user = getUserProfile(tokenResponse, provider);
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("name", user.getName());
        valueMap.put("role", user.getRole());
        //jwt token 발급
        String accessToken = JwtUtils.generateToken(valueMap, JwtConstants.ACCESS_EXP_TIME);
        String refreshToken = JwtUtils.generateToken(valueMap, JwtConstants.REFRESH_EXP_TIME);

        return new LoginDTO(accessToken, refreshToken, user.getId());*/
        String param = "grant_type=authorization_code&client_id="+REST_API_KEY+"&redirect_uri="+REDIRECT_URI+"&client_secret="+CLIENT_SECRET+"&code="+code;
        String rtn = httpCallService.Call("POST", TOKEN_URI, "", param);
        //httpSession.setAttribute("token", Trans.token(rtn, new JsonParser()));
        String[] split = rtn.split(",");
        for (String s : split) {
            String[] split1 = s.split(":");
            httpSession.setAttribute(split1[0].replace("\"", "").replace("{", ""), split1[1].replace("\"", "").replace("}", ""));
            log.info(split1[0].replace("\"", "").replace("{", ""));
            log.info(split1[1].replace("\"", "").replace("}", ""));

        }
        String[] split1 = split[0].split(":");
        String[] split2 = split[2].split(":");

        String[] profile = getProfile().split(",");


        String[] split3 = profile[0].split(":");
        String[] split4 = profile[2].split(":");
        String socialId = split3[1];
        String name = split4[2].replace("}", "");
        Optional<User> bySocialId = userRepository.findBySocialId(socialId);
        User member = bySocialId.orElseGet(() -> saveSocialMember(socialId, name));

        return new LoginDTO(split1[1].replace("\"", ""), split2[1].replace("\"", ""), member.getId());

        //log.info(rtn);
        //return new RedirectView("/index.html");


    }

    public String getProfile() {
        String uri = KAKAO_API_HOST + "/v2/user/me";
        return httpCallService.CallwithToken("GET", uri, httpSession.getAttribute("access_token").toString());
    }

    private MultiValueMap<String, String> tokenRequest(String code, ClientRegistration provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("client_id",provider.getClientId());
        return formData;
    }

    //kakao로부터 access token, refresh token 전달 받음
    private OAuth2AccessTokenResponse getToken(String code, ClientRegistration provider) {


        WebClient webClient = WebClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .build();
        // 카카오 서버에 요청 보내기 & 응답 받기
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", "0079af8e66a2f897721d5419a0933b70")
                        .queryParam("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao")
                        .queryParam("client_secret", "zswRxlhG4BxDtwdibpuOIRyw6b75yekK").queryParam("code", code).build())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .retrieve().bodyToMono(OAuth2AccessTokenResponse.class).block();
       /* return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OAuth2AccessTokenResponse.class)
                .block();*/
    }

    //kakao로부터 User Resource를 전달받음
    private Map<String, Object> getUserAttributes(ClientRegistration provider, OAuth2AccessTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken().getTokenValue()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }



    private User getUserProfile(OAuth2AccessTokenResponse tokenResponse, ClientRegistration provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(userAttributes);

        String socialId = kakaoUserInfo.getSocialId();
        String name = kakaoUserInfo.getName();

        Optional<User> bySocialId = userRepository.findBySocialId(socialId);
        User member = bySocialId.orElseGet(() -> saveSocialMember(socialId, name));

        return member;
    }
    public User saveSocialMember(String socialId, String name) {
        User newMember = User.builder().socialId(socialId).name(name).role(Role.GUEST).build();
        return userRepository.save(newMember);
    }


/*

    private final HttpSession httpSession;

    @Autowired
    public HttpCallService httpCallService;


    @Value("${client-id}")
    private String REST_API_KEY;

    @Value("${redirect-uri}")
    private String REDIRECT_URI;

    @Value("${authorize-uri}")
    private String AUTHORIZE_URI;

    @Value("${token-uri}")
    public String TOKEN_URI;

    @Value("${client-secret}")
    private String CLIENT_SECRET;

    @Value("${kakao-api-host}")
    private String KAKAO_API_HOST;


    public RedirectView goKakaoOAuth() {
        return goKakaoOAuth("");
    }

    public RedirectView goKakaoOAuth(String scope) {

        String uri = AUTHORIZE_URI+"?redirect_uri="+REDIRECT_URI+"&response_type=code&client_id="+REST_API_KEY;
        if(!scope.isEmpty()) uri += "&scope="+scope;

        return new RedirectView(uri);
    }

    public RedirectView loginCallback(String code) {
        String param = "grant_type=authorization_code&client_id="+REST_API_KEY+"&redirect_uri="+REDIRECT_URI+"&client_secret="+CLIENT_SECRET+"&code="+code;
        String rtn = httpCallService.Call("POST", TOKEN_URI, "", param);
        httpSession.setAttribute("token", Trans.token(rtn, new JsonParser()));
        String[] split = rtn.split(",");
        httpSession.setAttribute("accessToken", split[0].substring(16, split[0].length()-1));
        httpSession.setAttribute("accessToken", split[0].substring(16, split[0].length()-1));
        httpSession.setAttribute("accessToken", split[0].substring(16, split[0].length()-1));

        return new LoginDTO(split[0].substring(16, split[0].length()-1), split[2].substring(17, split[2].length()-1), );
    }*/

}