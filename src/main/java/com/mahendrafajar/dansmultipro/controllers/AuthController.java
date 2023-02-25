package com.mahendrafajar.dansmultipro.controllers;

import com.mahendrafajar.dansmultipro.configurations.JwtTokenService;
import com.mahendrafajar.dansmultipro.dto.AuthData;
import com.mahendrafajar.dansmultipro.dto.ResponseData;
import com.mahendrafajar.dansmultipro.dto.UserData;
import com.mahendrafajar.dansmultipro.models.User;
import com.mahendrafajar.dansmultipro.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@Valid @RequestBody UserData userData, Errors errors){
        validation(errors);

        User user = modelMapper.map(userData, User.class);
        userService.signUp(user);

        return ResponseEntity.ok(userData);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@Valid @RequestBody AuthData authData, Errors errors) throws Exception {
        validation(errors);

        Authentication authentication = authenticate(authData.getUsername(), authData.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HashMap<String, String> responseData = new HashMap<>();
        responseData.put("token", jwtTokenService.generateToken(authentication));

        return ResponseEntity.ok(responseData);
    }

    private void validation(Errors errors){
        if(errors.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getAllErrors().get(0).getDefaultMessage());
        }
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
