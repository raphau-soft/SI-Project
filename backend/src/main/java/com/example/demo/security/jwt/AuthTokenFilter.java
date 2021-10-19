package com.example.demo.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.security.MyUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired 
	private JwtUtils jwtUtils;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 try{
	            String jwt = parseJwt(request);
	            if(jwt != null && jwtUtils.validateJwtToken(jwt)){
	                String username = jwtUtils.getUserNameFromJwtToken(jwt);

	                UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities());
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            } else {
	                //logger.error("Cannot set the Security Context");
	            	// TODO: take care of error
	            }
	        } catch (ExpiredJwtException ex){
	            String isRefreshToken = request.getHeader("isRefreshToken");
	            String requestURL = request.getRequestURL().toString();
	            if(isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
	                allowForRefreshToken(ex, request);
	            } else
	            	request.setAttribute("exception", ex.getMessage());
	        } catch (BadCredentialsException ex){
	        	request.setAttribute("exception", ex);
	        } catch (Exception ex){
	            //logger.error(ex.toString());
	        	// TODO: take care of error
	        }

	        filterChain.doFilter(request, response);
	}
	
	private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        request.setAttribute("claims", ex.getClaims());
    }
	
	private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");

        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

}




















