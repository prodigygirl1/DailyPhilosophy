package com.studentproj.DailyPhilosophy.service;

import com.studentproj.DailyPhilosophy.dao.ProfileRepository;
import com.studentproj.DailyPhilosophy.dto.RegisterDto;
import com.studentproj.DailyPhilosophy.models.Profile;
import com.studentproj.DailyPhilosophy.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class ProfileService implements UserDetailsService {
    @Autowired
    private ProfileRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    public void register(Profile profile) {
        profile.setPassword(encoder.encode(profile.getPassword()));
        userRepository.save(profile);
    }

    public String auth(String login, String password) {
        Profile profile = userRepository.findByLogin(login);
        if (profile == null)
            return null; // возврат сообщения
        if (encoder.matches(password, profile.getPassword())) {
            return jwtProvider.generateToken(login);
        }

        return null; // возврат сообщения
    }

    public void update(Profile curr_user, RegisterDto profile) {
        curr_user.setPassword(encoder.encode(profile.getPassword()));
        curr_user.setLogin(profile.getLogin());
        userRepository.save(curr_user);
    }

}
