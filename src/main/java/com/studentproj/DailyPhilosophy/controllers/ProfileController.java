package com.studentproj.DailyPhilosophy.controllers;

import com.studentproj.DailyPhilosophy.dto.LoginDto;
import com.studentproj.DailyPhilosophy.dto.RegisterDto;
import com.studentproj.DailyPhilosophy.mapper.ProfileMapper;
import com.studentproj.DailyPhilosophy.models.Article;
import com.studentproj.DailyPhilosophy.models.Profile;
import com.studentproj.DailyPhilosophy.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
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
        return (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PutMapping("/update")
    public void update(@Valid @RequestBody RegisterDto registerDto) {
        Profile current_user = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileService.update(current_user, registerDto);
    }

    @PostMapping("/like/{id}")
    public void like(@PathVariable Long id) {
        Profile current_user = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileService.like(current_user, id);
    }

    @PostMapping("/remove_like/{id}")
    public void remove_like(@PathVariable Long id) {
        Profile current_user = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileService.remove_like(current_user, id);
    }

    @GetMapping(value = "/get_likes")
    public Set<Article> get_likes() {
        Profile current_user = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return profileService.get_likes(current_user);
    }


}
