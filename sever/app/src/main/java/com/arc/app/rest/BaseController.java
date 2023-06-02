package com.arc.app.rest;

import com.arc.app.entity.base.Role;
import com.arc.app.repository.base.UserRepository;
import com.arc.app.request.base.RoleRequest;
import com.arc.app.request.base.UserSecurity;
import com.arc.app.response.ResponseList;
import com.arc.app.response.ResponseObject;
import com.arc.app.service.base.RoleService;
import com.arc.app.utils.constants.ARCConstants;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class BaseController {
    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleService roleService;

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorization = request.getHeader(AUTHORIZATION);
        if(authorization != null && authorization.startsWith("Bearer ")) {
            try {
                String token = authorization.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                UserSecurity user = userRepository.findByUsername(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withIssuedAt(Date.from(Instant.now()))
                        .withExpiresAt(new Date(System.currentTimeMillis() + ARCConstants.TIME_OUT_TOKEN * 60 * 1000))
                        .withClaim("roles", user.getRoles().stream().map(RoleRequest::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> result = new HashMap<>();
                result.put("refresh_token", access_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), result);
            } catch (Exception e) {
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @GetMapping("/get-all-role")
    public ResponseList<Role> getAllRole() {
        return roleService.getAllRoles();
    }

    @PostMapping("/save-role")
    public ResponseObject saveRole(@RequestBody RoleRequest request) {
        return roleService.saveRole(request);
    }
}
