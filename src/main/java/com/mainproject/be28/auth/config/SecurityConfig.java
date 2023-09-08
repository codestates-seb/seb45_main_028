package com.mainproject.be28.auth.config;

import com.mainproject.be28.auth.handler.MemberAuthenticationEntryPoint;
import com.mainproject.be28.auth.handler.MemberAuthenticationFailureHandler;
import com.mainproject.be28.auth.handler.MemberAuthenticationSuccessHandler;
import com.mainproject.be28.auth.handler.MemberDeniedHandler;
import com.mainproject.be28.auth.jwt.JwtAuthenticationFilter;
import com.mainproject.be28.auth.jwt.JwtTokenizer;
import com.mainproject.be28.auth.jwt.JwtVerificationFilter;
import com.mainproject.be28.auth.refresh.RefreshTokenRepository;
import com.mainproject.be28.auth.userdetails.MemberAuthority;
import com.mainproject.be28.member.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final MemberAuthority memberAuthority;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

//    @Value("${spring.security.oauth2.client.registration.google.client-id}")
//    private String clientId;
//
//    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
//    private String clientSecret;

    public SecurityConfig(JwtTokenizer jwtTokenizer,MemberAuthority memberAuthority, RefreshTokenRepository refreshTokenRepository, MemberRepository memberRepository) {
        this.jwtTokenizer = jwtTokenizer;
        this.memberAuthority = memberAuthority;
        this.refreshTokenRepository = refreshTokenRepository;
        this.memberRepository = memberRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .cors(Customizer.withDefaults())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable() //폼로그인 비활성화
                .httpBasic().disable() //request 전송때마다 헤더에 name pw 실어서 인증하는 방식 비활성화
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint(refreshTokenRepository,jwtTokenizer, memberRepository))
                .accessDeniedHandler(new MemberDeniedHandler())
                .and()
                .apply(new CustomFilterConfiguration())
                .and()
//                .oauth2Client(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.GET, "/member/myPage/**").hasRole("USER")
                .anyRequest().permitAll());
//               .and()
//                .oauth2Login(oauth2 -> oauth2
//                        .successHandler(new oAuth2SuccessHandler(jwtTokenizer,memberAuthority,memberRepository))
//                );

        return http.build();
    }
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository(){
//        var clientRegistration = clientRegistration();
//
//        return new InMemoryClientRegistrationRepository(clientRegistration);
//    }
////    private ClientRegistration clientRegistration(){
////        return CommonOAuth2Provider
////                .GOOGLE
////                .getBuilder("google")
////                .clientId(clientId)
////                .clientSecret(clientSecret)
////                .build();
////    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "PATCH", "DELETE", "POST", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    public class CustomFilterConfiguration extends AbstractHttpConfigurer<CustomFilterConfiguration, HttpSecurity> {

        @Override
        public void configure(HttpSecurity builder) throws Exception {

            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, refreshTokenRepository);
            jwtAuthenticationFilter.setFilterProcessesUrl("/member/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer,memberAuthority);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);


        }
    }
}