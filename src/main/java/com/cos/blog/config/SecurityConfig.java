package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration // 빈 등록
@EnableWebSecurity
public class SecurityConfig{

    @Bean   // IoC가 됨
    BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer ignore() {
        return w -> w.ignoring().requestMatchers("/static/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain fliterChain(HttpSecurity http) throws Exception {

        //csrf 토큰 비활성화 (테스트 시 걸어두는게 좋음)
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(a -> {
            a.requestMatchers(RegexRequestMatcher.regexMatcher("/board/\\d+" + "/dummy/\\d+")).permitAll()
                    .requestMatchers("/users/**", "/board/**").authenticated()
                    .anyRequest().permitAll();
        });

        http.formLogin(
                f -> {
                    f
                            .loginPage("/auth/loginForm")
                            .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
                            .defaultSuccessUrl("/")
                            .failureUrl("/auth/loginForm");

                });

        //인증 주소 설정
        return http.build();
    }

}
