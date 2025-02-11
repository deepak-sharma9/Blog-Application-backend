package org.blogapplication.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.blogapplication.security.CustomUserDetailService;
import org.blogapplication.security.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailService customUserDetailService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailService customUserDetailService) {
        this.jwtService = jwtService;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");


        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            //  Validate token before proceeding
            if (jwtService.validateToken(token)) {
                String usernameFromToken = jwtService.getUserNameFromToken(token);

                //  Prevent re-authentication if already authenticated
                if (usernameFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = customUserDetailService.loadUserByUsername(usernameFromToken);

                    //  Debugging log (Remove later if not needed)
                    System.out.println("Authenticating user: " + usernameFromToken);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        //  Ensure filter chain continues, avoiding infinite loops
        filterChain.doFilter(request, response);
    }
}
