package com.vaxflow.core.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomAuthHandler customAuthHandler;

    public SecurityConfig(CustomAuthHandler customAuthHandler) {
        this.customAuthHandler = customAuthHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/login",
                    "/forgot-password",
                    "/reset-password",
                    "/error",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/favicon.ico"
                ).permitAll()

                // Quan tri he thong & Nhat ky hoat dong: Chi danh cho ADMIN
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                // Quan ly nhan su: ADMIN & MANAGER
                .requestMatchers("/employees/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")

                // Kho vac xin: ADMIN, MANAGER, STAFF, DOCTOR
                .requestMatchers("/vaccines/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_STAFF", "ROLE_DOCTOR")

                // Hoa don & Thu phi: ADMIN, MANAGER, STAFF
                .requestMatchers("/invoices/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_STAFF")

                // Khach hang & Ho so tiem: ADMIN, MANAGER, DOCTOR, NURSE, STAFF
                .requestMatchers("/customers/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_DOCTOR", "ROLE_NURSE", "ROLE_STAFF")

                // Con lai yeu cau xac thuc (Dashboard, Profile, ...)
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customAuthHandler)
                .failureHandler(customAuthHandler)
                .permitAll()
            )

            //ghi nho dang nhap
            .rememberMe(remember -> remember
                .key("vaxflow-secure-remember-me-key-2026")
                .tokenValiditySeconds(7 * 24 * 3600) // 7 ngay
                .rememberMeParameter("remember-me")
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .deleteCookies("JSESSIONID", "remember-me")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            );

        return http.build();
    }
}