package com.nahom.login3.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nahom.login3.entity.Puser;
import com.nahom.login3.repository.PuserRepository;

@Configuration
public class PuserDetailService implements UserDetailsService {

        @Autowired
        private PuserRepository repo; 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Puser> user = repo.findByEmail(username);
        return user.map(PuserDetails ::new).orElseThrow(()->new UsernameNotFoundException(username+ "who are u"));
    }
    
}
