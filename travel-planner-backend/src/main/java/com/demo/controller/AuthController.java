package com.demo.controller;

	

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.AuthRequest;
import com.demo.dto.AuthResponse;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.security.JwtTokenUtil;
@CrossOrigin(origins = "http://localhost:3000")
	@RestController
	@RequestMapping("/api/auth")
	public class AuthController {

	    @Autowired private AuthenticationManager authManager;
	    @Autowired private UserRepository userRepository;
	    @Autowired private JwtTokenUtil jwtUtil;
	    @Autowired private PasswordEncoder passwordEncoder;

	    @PostMapping("/register")
	    public ResponseEntity<String> register(@RequestBody User user) {
	        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
	        }
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        userRepository.save(user);
	        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
	    }


	    @PostMapping("/login")
	    public AuthResponse login(@RequestBody AuthRequest request) {
	        authManager.authenticate(new UsernamePasswordAuthenticationToken(
	                request.getUsername(), request.getPassword()));
	        var user = userRepository.findByUsername(request.getUsername()).get();
	        String token = jwtUtil.generateToken(user);
	        return new AuthResponse(token);
	    }
	}



