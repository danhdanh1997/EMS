package com.xuandanh.ems.restapi;

import com.xuandanh.ems.domain.User;
import com.xuandanh.ems.dto.ResponseDto;
import com.xuandanh.ems.dto.user.SignInDto;
import com.xuandanh.ems.dto.user.SignInResponseDto;
import com.xuandanh.ems.dto.user.SignupDto;
import com.xuandanh.ems.exceptions.AuthenticationFailException;
import com.xuandanh.ems.exceptions.CustomException;
import com.xuandanh.ems.repository.UserRepository;
import com.xuandanh.ems.service.AuthenticationService;
import com.xuandanh.ems.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class UserController {
    UserRepository userRepository;
    AuthenticationService authenticationService;
    UserService userService;

    @GetMapping("/all")
    public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        return userRepository.findAll();
    }

    @PostMapping("/signup")
    public ResponseDto Signup(@RequestBody SignupDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }

    //TODO token should be updated
    @PostMapping("/signIn")
    public SignInResponseDto Signup(@RequestBody SignInDto signInDto) throws CustomException {
        return userService.signIn(signInDto);
    }

//    @PostMapping("/updateUser")
//    public ResponseDto updateUser(@RequestParam("token") String token, @RequestBody UserUpdateDto userUpdateDto) {
//        authenticationService.authenticate(token);
//        return userService.updateUser(token, userUpdateDto);
//    }


//    @PostMapping("/createUser")
//    public ResponseDto updateUser(@RequestParam("token") String token, @RequestBody UserCreateDto userCreateDto)
//            throws CustomException, AuthenticationFailException {
//        authenticationService.authenticate(token);
//        return userService.createUser(token, userCreateDto);
//    }
}
