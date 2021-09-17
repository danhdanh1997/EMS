package com.xuandanh.ems.service;

import com.xuandanh.ems.config.MessageStrings;
import com.xuandanh.ems.domain.AuthenticationToken;
import com.xuandanh.ems.domain.User;
import com.xuandanh.ems.dto.ResponseDto;
import com.xuandanh.ems.dto.user.SignInDto;
import com.xuandanh.ems.dto.user.SignInResponseDto;
import com.xuandanh.ems.dto.user.SignupDto;
import com.xuandanh.ems.dto.user.UserCreateDto;
import com.xuandanh.ems.enums.ResponseStatus;
import com.xuandanh.ems.enums.Roles;
import com.xuandanh.ems.exceptions.AuthenticationFailException;
import com.xuandanh.ems.exceptions.CustomException;
import com.xuandanh.ems.repository.UserRepository;
import com.xuandanh.ems.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.xuandanh.ems.config.MessageStrings.USER_CREATED;

@Service
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;
    AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(UserService.class);


    public ResponseDto signUp(SignupDto signupDto) throws CustomException {
        // Check to see if the current email address has already been registered.
        if (Helper.notNull(userRepository.findByEmail(signupDto.getEmail()))) {
            // If the email address has been registered then throw an exception.
            throw new CustomException("User already exists");
        }
        // first encrypt the password
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }
        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), Roles.user, encryptedPassword);

        User createdUser;
        try {
            // save the User
            createdUser = userRepository.save(user);
            // generate token for user
            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);
            // save token in database
            authenticationService.saveConfirmationToken(authenticationToken);
            // success in creating
            return new ResponseDto(ResponseStatus.success.toString(), USER_CREATED);
        } catch (Exception e) {
            // handle signup error
            throw new CustomException(e.getMessage());
        }
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws CustomException {
        // first find User by email
        User user = userRepository.findByEmail(signInDto.getEmail());
        if (!Helper.notNull(user)) {
            throw new AuthenticationFailException("user not present");
        }
        try {
            // check if password is right
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                // password does not match
                throw new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if (!Helper.notNull(token)) {
            // token not present
            throw new CustomException("token not present");
        }

        return new SignInResponseDto("success", token.getToken());
    }


    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }

    public ResponseDto createUser(String token, UserCreateDto userCreateDto) throws CustomException, AuthenticationFailException {
        User creatingUser = authenticationService.getUser(token);
        if (!canCrudUser(creatingUser.getRole())) {
            // user can't create new user
            throw new AuthenticationFailException(MessageStrings.USER_NOT_PERMITTED);
        }
        String encryptedPassword = userCreateDto.getPassword();
        try {
            encryptedPassword = hashPassword(userCreateDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }

        User user = new User(userCreateDto.getFirstName(), userCreateDto.getLastName(), userCreateDto.getEmail(), userCreateDto.getRole(), encryptedPassword);
        User createdUser;
        try {
            createdUser = userRepository.save(user);
            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);
            authenticationService.saveConfirmationToken(authenticationToken);
            return new ResponseDto(ResponseStatus.success.toString(), USER_CREATED);
        } catch (Exception e) {
            // handle user creation fail error
            throw new CustomException(e.getMessage());
        }

    }

    boolean canCrudUser(Roles role) {
        return role == Roles.admin || role == Roles.manager;
    }

    boolean canCrudUser(User userUpdating, Integer userIdBeingUpdated) {
        Roles role = userUpdating.getRole();
        // admin and manager can crud any user
        if (role == Roles.admin || role == Roles.manager) {
            return true;
        }
        // user can update his own record, but not his role
        return role == Roles.user && userUpdating.getId() == userIdBeingUpdated;
    }
}