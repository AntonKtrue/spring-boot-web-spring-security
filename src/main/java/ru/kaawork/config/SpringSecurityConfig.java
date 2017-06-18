package ru.kaawork.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
// http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
// Switch off the Spring Boot security configuration
//@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

   /*
    @Autowired
    PersistentTokenRepository tokenRepository;*/
    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/about", "/kaawork","/assert/**","/fonts/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
               /* .loginProcessingUrl("/login")
                .usernameParameter("ssoId")
                .passwordParameter("password")
                .permitAll()
                //.and().rememberMe().tokenRepository(tokenRepository)
                //.tokenValiditySeconds(86400)
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
http.csrf().disable()
                .authorizeRequests()
					.antMatchers("/", "/home", "/about").permitAll()
					.antMatchers("/admin/**").hasAnyRole("ADMIN")
					.antMatchers("/user/**").hasAnyRole("USER")
					.anyRequest().authenticated()
                .and()
                .formLogin()
					.loginPage("/login")
					.permitAll()
					.and()
                .logout()
					.permitAll()
					.and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
      /*  http.authorizeRequests().antMatchers("/kaawork*//**").permitAll()
                //.access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
                .antMatchers("/newuser*//**", "/delete-user-*").access("hasRole('ADMIN')")
                .antMatchers("/edit-user-*").access("hasRole('ADMIN') or hasRole('DBA')")
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("ssoId")
                .passwordParameter("password")
                .and()
                .rememberMe().tokenRepository(tokenRepository)
                .tokenValiditySeconds(86400).and().csrf().and().exceptionHandling().accessDeniedPage("/Access_Denied");*/

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }

   /* @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
    }
*/
    /*
    //Spring Boot configured this already.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }*/

}
