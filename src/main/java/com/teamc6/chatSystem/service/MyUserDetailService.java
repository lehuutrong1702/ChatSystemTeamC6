package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.security.MyUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("abc");
        var u  = userRepository.findByUsername(username);
//        System.out.println(u.get().getPassword());
        return u.map(MyUserDetail::new).orElseThrow(()->new UsernameNotFoundException("Username not found " + username));
    }
}
