package com.sunil.blog.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunil.blog.config.AppConstants;
import com.sunil.blog.entities.User;
import com.sunil.blog.exceptions.ResourceNotFoundException;
import com.sunil.blog.repositories.UserRepo;
import com.sunil.blog.security.JwtTokenHelper;

@Component
public class RequestHelper {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserRepo userRepo;

    public User getUserFromRequest(HttpServletRequest request) {
        String requestToken = request.getHeader(AppConstants.AUTH_HEADER_NAME);

        if (requestToken == null || !(requestToken.startsWith("Bearer")))
            return null;

        String token = requestToken.substring(7);
        String userName = this.jwtTokenHelper.getUsernameFromToken(token);

        if (userName == null)
            return null;

        User user = this.userRepo.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userName));

        return user;
    }
}
