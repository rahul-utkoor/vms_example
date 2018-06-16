package com.aizant.vms.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aizant.vms.model.UserLoginLogoutAudit;
import com.aizant.vms.repo.UserLoginLogoutAuditRepo;
import com.aizant.vms.security.JwtAuthenticationRequest;
import com.aizant.vms.security.JwtTokenUtil;
import com.aizant.vms.service.JwtAuthenticationResponse;

@RestController
public class AuthenticationRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    UserLoginLogoutAuditRepo userLoginLogoutAuditRepo;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest){
    	try{
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        
        
        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
        UserLoginLogoutAudit audit = new UserLoginLogoutAudit();
        audit.setLastLogin(timestamp);
        audit.setUserName(authenticationRequest.getUsername());
        audit.setCenterId(Long.valueOf(authenticationRequest.getCenterId()));
        
        userLoginLogoutAuditRepo.save(audit);
        
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    	//return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);

        if (jwtTokenUtil.canTokenBeRefreshed(token/*, user.getLastPasswordResetDate()*/)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/user/logout")
    @ResponseBody
    public ResponseEntity<String> userLogout(HttpServletRequest request){    	
    	String token = request.getHeader(tokenHeader).substring(7);
    	jwtTokenUtil.invalidateToken(token);    	
    	return ResponseEntity.ok("User Logged out");
    }

}
