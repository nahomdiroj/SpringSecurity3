package com.nahom.login3.coontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahom.login3.entity.Puser;
import com.nahom.login3.repository.PuserRepository;

@RestController
@RequestMapping
public class Controller {

    @Autowired
    private PuserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(){
        return "no login";
    }
    
    @PostMapping("/user/save")
    public ResponseEntity<Object> saveUser(@RequestBody Puser puser){
        puser.setPassword(passwordEncoder.encode((puser.getPassword())));
        Puser result = repo.save(puser);

        if(result.getId()>0){
            return ResponseEntity.ok("user saved");
        }

        return ResponseEntity.status(404).body("Not saved");
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getUser(){
        return ResponseEntity.ok(repo.findAll());
    }

    
    @GetMapping("/list/id")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Object> getUserid(){
        return ResponseEntity.ok(repo.findByEmail(getLoggedInUser().getUsername()));
    }

    public UserDetails getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null && authentication.getPrincipal() instanceof UserDetails){
            return( UserDetails) authentication.getPrincipal();
        }
        return null;
    }

}
