package com.flowershop.myflowershop.config;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecuriyConfig {
    @Autowired
    private com.flowershop.myflowershop.services.UserSecurityService userSecurityService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean
    @SuppressWarnings("deprecation")
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{
        security.csrf().disable()
        .cors().disable()
        .authorizeRequests()
        .requestMatchers("/registration", "/login").permitAll()
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        .anyRequest().authenticated()
        .and().formLogin().loginPage("/login")
        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
        .and().userDetailsService(userSecurityService);
        
        return security.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userSecurityService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

        
}
