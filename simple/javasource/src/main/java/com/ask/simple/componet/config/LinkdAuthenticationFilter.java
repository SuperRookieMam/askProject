package com.ask.simple.componet.config;

import com.ask.simple.entity.OAthUserDetailes;
import com.ask.simple.service.OAthUserDetailesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class LinkdAuthenticationFilter  extends OncePerRequestFilter {
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
    protected void doFilterInternal( HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.username);
        String username =ObjectUtils.isEmpty(authHeader)?request.getParameter(this.username):authHeader;
        UserDetails userDetails =null;
         if (ObjectUtils.isEmpty(username)){
            String weixin =   request.getHeader(this.weixinaccount);
            if (!ObjectUtils.isEmpty(weixin)) {
              OAthUserDetailes oAthUserDetailes =  oAthUserDetailesService.findByCredentials(weixin);
              if (ObjectUtils.isEmpty(oAthUserDetailes)) {
                  oAthUserDetailes =new OAthUserDetailes();
                  oAthUserDetailes.setUsername(UUID.randomUUID().toString().replaceAll("-",""));
                  oAthUserDetailes.setPassword("123456");
                  oAthUserDetailes.setCredentials(weixin);
                  oAthUserDetailes.setLock(false);
                  oAthUserDetailes.setEnabled(true);
                  oAthUserDetailes.setEnabled(false);
                  oAthUserDetailes = oAthUserDetailesService.insertByEntity(oAthUserDetailes);
                  userDetails = oAthUserDetailes;
                  }
                }
             }else {
                 //如果登录过证明
                 userDetails = this.oAthUserDetailesService.loadUserByUsername(username);
             }
             UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
