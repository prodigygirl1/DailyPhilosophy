package com.studentproj.DailyPhilosophy.controllers;

import com.studentproj.DailyPhilosophy.dto.LoginDto;
import com.studentproj.DailyPhilosophy.dto.RegisterDto;
import com.studentproj.DailyPhilosophy.mapper.ProfileMapper;
import com.studentproj.DailyPhilosophy.models.Profile;
import com.studentproj.DailyPhilosophy.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileMapper profileMapper;

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterDto registerDto) {
        Profile profile = profileMapper.fromRegisterDto(registerDto);

        profileService.register(profile);
    }

    @PostMapping("/auth")
    public String auth(@RequestBody LoginDto dto) {
        return profileService.auth(dto.getLogin(), dto.getPassword());
    }

    @GetMapping("/me")
    public Profile me(@AuthenticationPrincipal Profile profile) {
//        return profile;

        return (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
