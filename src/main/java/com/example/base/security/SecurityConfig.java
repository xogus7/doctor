package com.example.base.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig { //WebSecurityConfigurerAdapter was deprecated

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }

    /**
     * filterChain 메서드는 SecurityFilterChain 인스턴스를 빈으로 등록합니다.
     * 이 메서드는 Spring Security 의 기본 보안 필터 체인을 구성합니다.
     * */
    @Bean

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //CSRF 공격 방지 기능을 사용하지 않겠다는 것을 설정합니다.
                .httpBasic().disable()// Http Basic 인증 방식을 사용하지 않겠다는 것을 설정합니다.
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()//위의 한줄은  서버 측 세션을 사용하지 않겠다는 것을 설정합니다.
                .authorizeRequests()//여기부터 HTTP 요청에 대한 보안 규칙을 정의하기 시작합니다.
                .antMatchers("/login").permitAll() // "/login" 경로로 들어오는 요청에 대해 인증을 거치지 않겠다는 것을 설정합니다.
//                .requestMatchers("/home").permitAll() // "/" 경로로 들어오는 요청에 대해, "USER" 권한을 가진 사용자만 인증을 거치도록 설정합니다.                .requestMatchers("/api/user").permitAll()
                .antMatchers("/patients").hasRole("USER")
                .anyRequest().authenticated() // 이외에
                .and()
                .logout().logoutSuccessUrl("/login")
                .and()
                .oauth2Login().loginPage("/login") // 이하  OAuth 2.0 기반 로그인 기능을 설정합니다.
//                .loginProcessingUrl("/home")
                .defaultSuccessUrl("/patients")
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
        ;
        return http.build();
    }
}
