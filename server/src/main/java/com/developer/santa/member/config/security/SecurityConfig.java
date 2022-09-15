package com.developer.santa.member.config.security;

import com.developer.santa.member.config.properties.AppProperties;
import com.developer.santa.member.config.properties.CorsProperties;
import com.developer.santa.member.oauth.exception.RestAuthenticationEntryPoint;
import com.developer.santa.member.oauth.filter.TokenAuthenticationFilter;
import com.developer.santa.member.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.developer.santa.member.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.developer.santa.member.oauth.handler.TokenAccessDeniedHandler;
import com.developer.santa.member.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.developer.santa.member.oauth.service.PrincipalOAuth2UserService;
import com.developer.santa.member.oauth.token.AuthTokenProvider;
import com.developer.santa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig{
    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final PrincipalOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler accessDeniedHandler;

    private final MemberRepository memberRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/assets/**", "/h2-console/**", "/h2/**");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/members/login").permitAll()
                .antMatchers("/api/connect").permitAll()
                .antMatchers("/course/**").permitAll()
                .antMatchers("/local/**").permitAll()
                .antMatchers("/mountain/**").permitAll()
                .antMatchers("/weather/**").permitAll()
                .antMatchers(HttpMethod.GET, "/reviwboards/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(authorizationRequestRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*")
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository(){
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(appProperties, authorizationRequestRepository(), tokenProvider, memberRepository);
    }

    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(authorizationRequestRepository());
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 서버응답 시 json을 자바스크립트에서 처리할 수 있게
        config.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        config.addAllowedHeader(corsProperties.getAllowedHeaders()); // 모든 헤더의 응답을 허용
        config.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(","))); // 모든 get post put delete patch 요청 허용

        source.registerCorsConfiguration("/**", config);
        return source;
    }


}