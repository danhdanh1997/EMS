package com.xuandanh.ems.service;

import com.xuandanh.ems.domain.UserProfile;
import com.xuandanh.ems.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private UserProfileRepository userRepo;


    public void addProfile(UserProfile userProfile) {
        userRepo.save(userProfile);
    }

    public List<UserProfile> listProfiles(){
        return userRepo.findAll();
    }

}
