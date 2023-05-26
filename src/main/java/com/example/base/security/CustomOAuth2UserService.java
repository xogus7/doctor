package com.example.base.security;


import com.example.base.domain.SessionUser;
import com.example.base.domain.User;
import com.example.base.repository.UserRepository;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

/*
 * 이 코드는 Spring Security OAuth2를
 * 사용하여 사용자 인증 및 권한 부여를 처리하는 클래스를 정의하고 있다.
 * */
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    /**
     * UserRepository는 사용자 정보를 데이터베이스에서 검색 및 저장하기 위해 사용되며,
     * HttpSession은 사용자 정보를 세션에 저장하고 가져오는 데 사용됩니다.
     */
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public CustomOAuth2UserService(UserRepository userRepository, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    /**
     * loadUser 메서드에서는 DefaultOAuth2UserService를 사용하여 OAuth2UserRequest에 대한 OAuth2User 객체를 로드합니다.
     * 그러면 로그인하는 사용자의 공급자 등록 ID 및 사용자 이름 속성 이름과 같은 사용자 정보를 가져와서 OAuthAttributes 객체로 변환합니다.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    /**
     *  UserRepository 를 사용하여 OAuthAttributes 에서 가져온
     *  사용자 정보를 데이터베이스에 저장하거나 업데이트합니다.
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
