package Midas.Saessack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // H2 콘솔 경로에 대한 접근 허용
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2-console/**").permitAll()  // H2 콘솔 경로는 허용
                        .anyRequest().authenticated() // 그 외의 경로는 인증 필요
                )
                // CSRF 보호 비활성화 (H2 콘솔 사용 시 필수)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                // X-Frame-Options 비활성화 (iframe에서 콘솔을 열 수 있도록 허용)
                .headers(headers -> headers.frameOptions().disable());

        return http.build();
    }
}
