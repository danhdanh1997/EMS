package com.xuandanh.ems.service;

import com.xuandanh.ems.config.MessageStrings;
import com.xuandanh.ems.domain.AuthenticationToken;
import com.xuandanh.ems.domain.User;
import com.xuandanh.ems.exceptions.AuthenticationFailException;
import com.xuandanh.ems.repository.TokenRepository;
import com.xuandanh.ems.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    TokenRepository repository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        repository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return repository.findTokenByUser(user);
    }

    public User getUser(String token) {
        AuthenticationToken authenticationToken = repository.findTokenByToken(token);
        if (Helper.notNull(authenticationToken)) {
            if (Helper.notNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    public void authenticate(String token) throws AuthenticationFailException {
        if (!Helper.notNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        if (!Helper.notNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }
}