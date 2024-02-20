package com.example.studentbackend.service;

import com.example.studentbackend.config.UserInfoUserDetail;
import com.example.studentbackend.model.UserInfo;
import com.example.studentbackend.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
        UserInfoUserDetail userInfoUserDetail = userInfo.map(UserInfoUserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found" + username));
        return userInfoUserDetail;
    }
}
