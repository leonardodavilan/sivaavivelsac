package pe.com.avivel.sistemas.siva.auth;


import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/proveedores", "/api/proveedores/page/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/proveedores").permitAll()
                .antMatchers(HttpMethod.GET,"/api/insumos-proveedores").permitAll()
                .antMatchers(HttpMethod.POST,"/api/insumo-proveedor").permitAll()
                .antMatchers(HttpMethod.POST,"/api/insumos").permitAll()
                .antMatchers(HttpMethod.GET,"/api/insumos/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/programaciones").permitAll()
                .antMatchers(HttpMethod.GET,"/api/etapas/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/report/pdf").permitAll()
                /*.antMatchers(HttpMethod.GET, "/api/proveedores/{id}").hasAnyRole("ROLE_SANIDAD_USER", "ROLE_SANIDAD_ADMIN")
        .antMatchers(HttpMethod.POST, "/api/proveedores/upload").hasAnyRole("ROLE_SANIDAD_USER", "ROLE_SANIDAD_ADMIN")
        .antMatchers(HttpMethod.POST, "/api/proveedores").hasRole("ADMIN")
        .antMatchers("/api/proveedores/**").hasRole("ADMIN")*/
                .anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }


}
