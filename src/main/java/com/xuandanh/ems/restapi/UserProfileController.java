package com.xuandanh.ems.restapi;

import com.xuandanh.ems.common.ApiResponse;
import com.xuandanh.ems.domain.UserProfile;
import com.xuandanh.ems.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private UserProfileService userProfileService;

    @GetMapping("/")
    public ResponseEntity<List<UserProfile>> getUsers() {
        List<UserProfile> dtos = userProfileService.listProfiles();
        return new ResponseEntity<List<UserProfile>>(dtos, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSurvey(@RequestBody @Valid UserProfile profile) {
        userProfileService.addProfile(profile);
        return new ResponseEntity<>(new ApiResponse(true, "Profile has been created."), HttpStatus.CREATED);
    }
}
