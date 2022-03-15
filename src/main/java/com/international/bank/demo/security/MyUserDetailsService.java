package com.international.bank.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Below class used for prepare custom username and password,
 * this class uses UserDetailService class overriding loadUserByUsername method where we will set custom username and password.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User("shivam" , "shivam", new ArrayList<>());
    }
}
