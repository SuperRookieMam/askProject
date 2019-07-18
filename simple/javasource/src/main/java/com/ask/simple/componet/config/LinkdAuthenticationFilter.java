package com.ask.simple.componet.config;

import com.ask.simple.entity.OAthUserDetailes;
import com.ask.simple.service.OAthUserDetailesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class LinkdAuthenticationFilter extends OncePerRequestFilter {
    String username = "username";
    String password = "password";
    String weixinaccount = "weixinaccount";
    @Autowired
    private OAthUserDetailesService oAthUserDetailesService;

    /**
     * token filter.
     *
     * @param request     .
     * @param response    .
     * @param filterChain .
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (peromitALL(request)){
            filterChain.doFilter(request, response);
            return;
        }
        if (accessControlRequestHeaders(request,response)){
            return;
        }
        UserDetails userDetails =autoRegister(request,response);
        if (ObjectUtils.isEmpty(userDetails)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
       UsernamePasswordAuthenticationToken authentication =
               new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
    private  UserDetails autoRegister(HttpServletRequest request, HttpServletResponse response){
        String authHeader = request.getHeader(this.username);
        String username = ObjectUtils.isEmpty(authHeader)?request.getParameter(this.username):authHeader;
        UserDetails userDetails =null;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (ObjectUtils.isEmpty(username)){
            String weixin = request.getHeader(this.weixinaccount);
            if (!ObjectUtils.isEmpty(weixin)) {
                OAthUserDetailes oAthUserDetailes =  oAthUserDetailesService.findByCredentials(weixin);
                if (ObjectUtils.isEmpty(oAthUserDetailes)) {
                    oAthUserDetailes =new OAthUserDetailes();
                    oAthUserDetailes.setUsername(UUID.randomUUID().toString().replaceAll("-",""));
                    oAthUserDetailes.setPassword(bCryptPasswordEncoder.encode("123456"));
                    oAthUserDetailes.setCredentials(weixin);
                    oAthUserDetailes.setLock(false);
                    oAthUserDetailes.setEnabled(true);
                    oAthUserDetailes.setEnabled(false);
                    oAthUserDetailes = oAthUserDetailesService.insertByEntity(oAthUserDetailes);
                    userDetails = oAthUserDetailes;
                }
            }
        } else {
            //如果登录过证明
            userDetails = this.oAthUserDetailesService.loadUserByUsername(username);
            /*if (!ObjectUtils.isEmpty(userDetails)) {
                if (!bCryptPasswordEncoder.matches(request.getHeader(this.password),
                        userDetails.getPassword())){
                    userDetails =null;
                }
            }*/
        }
       return userDetails;
    }
    // 处理跨域请求
    private  boolean accessControlRequestHeaders(HttpServletRequest request, HttpServletResponse response){
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods", request.getMethod());
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            response.setStatus(HttpStatus.OK.value());
            return true;
        }else {
            //  解决跨域问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            return false;
        }
    }
   private boolean peromitALL(HttpServletRequest request) {
       String uri = request.getRequestURI();
       for (String path : SecurityConfig.permitPath ) {
           if (uri.contains(path)) {
               return true;
           }
       }
       return false;
   }
}
