package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ConfirmationTokenRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.payload.Email;
import com.example.demo.dto.payload.EmailSenderService;
import com.example.demo.dto.payload.JwtResponse;
import com.example.demo.dto.payload.LoginRequest;
import com.example.demo.dto.payload.MessageResponse;
import com.example.demo.dto.payload.RefreshTokenResponse;
import com.example.demo.dto.payload.SignupRequest;
import com.example.demo.entity.ConfirmationToken;
import com.example.demo.entity.User;
import com.example.demo.security.MyUserDetails;
import com.example.demo.security.jwt.JwtUtils;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
	
    @PostMapping("signin")
    @CrossOrigin(value = "*", maxAge = 3600)
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        if(!userRepository.existsByLoginAndEnabled(loginRequest.getLogin(), true)){
            return ResponseEntity.badRequest().body(new MessageResponse("Verify your email"));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
        System.out.println("---------" + authentication.isAuthenticated() + "-----------");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        List<String> roles = myUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                myUserDetails.getId(),
                myUserDetails.getUsername(),
                myUserDetails.getEmail(),
                roles
        ));
    }
    
    @GetMapping("/refreshtoken")
    @CrossOrigin(value = "*", maxAge = 3600)
    public RefreshTokenResponse refreshToken(HttpServletRequest request) throws Exception {
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtUtils.generateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return new RefreshTokenResponse(token);
    }
    
    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for(Map.Entry<String, Object> entry: claims.entrySet()){
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }
    
    @PostMapping("/signup")
    @CrossOrigin(value = "*", maxAge = 3600)
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) throws MessagingException {
        if(signupRequest.getLogin() == null || signupRequest.getLogin().length() < 4){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username's length should be at least 4"));
        }
        if(signupRequest.getEmail() == null || !signupRequest.getEmail().contains("@")){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is not valid"));
        }
        if(userRepository.existsByLogin(signupRequest.getLogin())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken"));
        }
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken"));
        }

        if(signupRequest.getPassword() == null || signupRequest.getPassword().length() < 5){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Password's length should be at least 5"));
        }

        User user = new User(0, signupRequest.getLogin(), signupRequest.getEmail(), false, encoder.encode(signupRequest.getPassword()), "ROLE_USER");
        userRepository.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("javaquiz123@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @GetMapping("/confirm-account")
    @CrossOrigin(value = "*", maxAge = 3600)
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken){
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null){
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: The link is invalid or broken"));
        }
        return ResponseEntity.ok(new MessageResponse("Account is verified"));
    }
    
    @PostMapping("/resendConfirmation")
    @CrossOrigin(value = "*", maxAge = 3600)
    public ResponseEntity<?> resendConfirmation(@RequestBody Email email) throws MessagingException {
        User user = userRepository.findByEmailIgnoreCase(email.getEmail());
        if(user == null) return ResponseEntity.badRequest().body(new MessageResponse("Error - User with email " + email + " doesn't exist"));
        if(user.isEnabled()) return ResponseEntity.badRequest().body(new MessageResponse("Error - User with email " + email + " is enabled"));
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByUser(user);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("javaquiz123@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"https://quiz-server-prz.herokuapp.com/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
        return ResponseEntity.ok(new MessageResponse("The mail was sent again"));
    }
    
}



























