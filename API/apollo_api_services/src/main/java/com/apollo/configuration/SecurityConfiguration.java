package com.apollo.configuration;

import com.apollo.constraint.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .cors().configurationSource(corsConfigurationSource())  // Thêm cấu hình CORS ở đây
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/register/**", "/api/login/**").permitAll()
                .antMatchers("/api/products/**", "/api/product-detail/**", "/api/cart-lines/**",
                        "/api/cart/**", "/api/search/**", "/api/payments/**", "/api/save-for-later/**",
                        "/api/stores/**", "/api/sellers/**", "/api/variant/**", "/api/image/**",
                        "/api/video/**", "/api/option-value/**", "/api/option/**", "/api/category/**",
                        "/api/store-category/**", "/api/bullet/**", "/api/attribute/**", "/api/hashtag/**",
                        "/api/payments/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/products", "/api/users/**", "/api/shippers/**",
                        "/api/cart-lines/**", "/api/search/**", "/api/reviews/**", "/api/sellers/**",
                        "/api/admins/**", "/api/stores/**", "/api/payments/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/products", "/api/users/**", "/api/shippers/**",
                        "/api/cart-lines/**", "/api/search/**", "/api/reviews/**", "/api/sellers/**",
                        "/api/admins/**", "/api/stores/**", "/api/payments/**").permitAll()
                .antMatchers("/api/users/**").hasAnyRole(Role.USER.toString())
                .antMatchers("/api/shippers/**").hasAnyRole(Role.SHIPPER.toString())
                .antMatchers("/api/admins/**").hasAnyRole(Role.ADMIN.toString())
                .antMatchers("/api/orders/{orderId}/submit").hasRole(Role.ADMIN.toString())
                .antMatchers("/api/orders/{orderId}/complete").hasRole(Role.SHIPPER.toString())
                .antMatchers("/api.orders/{orderId}/cancel").hasRole(Role.SHIPPER.toString())
                .antMatchers("/api/orders/{orderId}/accept").hasRole(Role.SHIPPER.toString())
                .antMatchers(HttpMethod.GET, "/api/orders/{orderId}").hasRole(Role.USER.toString())
                .antMatchers("/api/store/images/**").permitAll()
                .antMatchers("/api/product/images/**").permitAll()
                .antMatchers("/api/variant/images/**").permitAll()
                .antMatchers("/api/variant/image-list/**").permitAll()
                .antMatchers("/api/variant/**").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8888"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
