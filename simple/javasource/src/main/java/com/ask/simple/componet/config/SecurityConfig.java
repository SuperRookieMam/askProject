package com.ask.simple.componet.config;

import com.ask.simple.service.OAthUserDetailesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAthUserDetailesService oAthUserDetailesService;
    @Autowired
    private AuthenticationSuccessHandler loginSuccessHandler;
    @Autowired
    private LinkdAuthenticationFilter linkdAuthenticationFilter;
    @Autowired
    private AuthenticationFailureHandler loginFailureHandler;
    private  static final String[] permitAll ;
    protected static final String[] permitPath;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
             .authorizeRequests()
             .antMatchers(permitAll).permitAll()
             .anyRequest().authenticated()
             .and()
             .formLogin().successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler).permitAll(); //新增login form支持用户登录及授权;
        http.userDetailsService(oAthUserDetailesService);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        http.addFilterBefore(linkdAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        //        检测倒用没有登陆跳转登陆页面重新拦截修改401错误
        http.exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    @Bean
    public PasswordEncoder getPassWordEncoder() {
        // 使用系统的加密
        return new BCryptPasswordEncoder();
    }
    static   {
        List<String> templist =  new ArrayList<>();
        templist.add("/login/**");
        templist.add("/logout/**");
        templist.add("/fileInfo/preview/**");
        permitAll =  templist.toArray(new String[0]);
        permitPath = new String[templist.size()];
        for (int i = 0; i <templist.size() ; i++) {
            permitPath[i] = templist.get(i).substring(0,templist.get(i).lastIndexOf("/"));
        }
    }
}
