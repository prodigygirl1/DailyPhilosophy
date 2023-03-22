package com.studentproj.DailyPhilosophy.controllers;

import com.studentproj.DailyPhilosophy.dto.LoginDto;
import com.studentproj.DailyPhilosophy.dto.RegisterDto;
import com.studentproj.DailyPhilosophy.mapper.ProfileMapper;
import com.studentproj.DailyPhilosophy.models.Article;
import com.studentproj.DailyPhilosophy.models.Profile;
import com.studentproj.DailyPhilosophy.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> me(@AuthenticationPrincipal Profile profile) {
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails))
            return new ResponseEntity<>("Отказано в доступе", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>((Profile) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal(),
                HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody RegisterDto registerDto) {
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails))
            return new ResponseEntity<>("Отказано в доступе", HttpStatus.FORBIDDEN);
        Profile current_user = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileService.update(current_user, registerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<?> like(@PathVariable Long id) {
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails))
            return new ResponseEntity<>("Отказано в доступе",HttpStatus.FORBIDDEN);
        Profile current_user = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (profileService.like(current_user, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Статья не найдена", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/remove_like/{id}")
    public ResponseEntity<?> remove_like(@PathVariable Long id) {
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails))
            return new ResponseEntity<>("Отказано в доступе", HttpStatus.FORBIDDEN);
        Profile current_user = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (profileService.remove_like(current_user, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Статья не найдена", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/get_likes")
    public Set<Article> get_likes() {
        Profile current_user = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return profileService.get_likes(current_user);
    }


}
