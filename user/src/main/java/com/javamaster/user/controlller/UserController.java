package com.javamaster.user.controlller;

import com.javamaster.user.authentication.JwtUtils;
import com.javamaster.user.dao.UserRepository;
import com.javamaster.transport.dto.ResponseDto;
import com.javamaster.user.model.User;
import com.javamaster.user.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody User loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            HashMap<String, Object> response = new HashMap<>();

            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            response.put("token", jwt);
            response.put("username", userDetails.getUsername());
            response.put("role", userDetails.getAuthorities().toString());
            return new ResponseEntity<>(ResponseDto.builder().message("User logged in successfully").data(response).build(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(ResponseDto.builder().message("Invalid username or password").build(), HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User signUpRequest) {

        try{
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Username is already taken!");
            }
            // Create new user's account
            User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),signUpRequest.getRole().toString());

            userRepository.save(user);

            return  new ResponseEntity<>(ResponseDto.builder().message("User registered successfully").build(), HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(ResponseDto.builder().message("Invalid username or password").build(), HttpStatus.BAD_REQUEST);
        }

    }
}
