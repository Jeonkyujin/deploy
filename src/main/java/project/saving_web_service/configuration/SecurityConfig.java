package project.saving_web_service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// 403, "http.csrf.disable()" deprecated
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				// HTTP 요청에 대한 권한 설정, 모든 경로로 지정("/**")
				// permitAll() : 해당 요청에 대한 모든 사용자에게 접근 권한 부여
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
			);
		return http.build();
	}
}
