package com.campuscare.security;

import com.campuscare.model.User;
import com.campuscare.repo.UserRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class AuthSupport {

    private final UserRepository users;

    public AuthSupport(UserRepository users) {
        this.users = users;
    }

    public User current(HttpServletRequest req) {
        String h = req.getHeader("Authorization");

        if (h == null || !h.startsWith("Bearer "))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please sign in");

        String[] p = h.substring(7).split(":", 2);

        if (p.length != 2)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid session");

        User u = users.findById(Long.valueOf(p[0]))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!u.isEnabled() || !u.isApproved() || !u.getEmail().equals(p[1]))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session expired");

        return u;
    }

    public void require(User u, String... roles) {
        if (Arrays.stream(roles).noneMatch(r -> r.equals(u.getRole())))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
    }
}