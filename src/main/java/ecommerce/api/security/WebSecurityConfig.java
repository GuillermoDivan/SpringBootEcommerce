package ecommerce.api.security;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.List;

@Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    public class WebSecurityConfig {

        private final UserDetailsService userDetailsService;
        private final SecurityFilter securityFilter;


        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(config -> config.disable()) //Deshabilita statefull y pasa a stateless.
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                    //Corrobora qué urls tienen autorización sin autenticar y cuáles no.
                    .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/user/save").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/admin").hasRole("ADMIN");
                    auth.anyRequest().authenticated();})
                    .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

                    //Logout is deprecated without lambda!!
                    .logout((logout) -> logout.logoutUrl("/logout")
                            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
                    .userDetailsService(userDetailsService)
                    .build();
        }

    @Bean
    public AuthenticationManager authenticationManager() {
        //Proveedor que configura qué tipo de service y de codificador se usan.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        List<AuthenticationProvider> providers =  List.of(authProvider);
        //El constructor requiere enviar lista.
        return new ProviderManager(providers);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
