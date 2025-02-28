package org.maping.maping.api.user.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.maping.maping.common.utills.ULID.ULIDUtill;
import org.maping.maping.common.utills.users.oauth.google.dto.GoogleUserInfoResponse;
import org.maping.maping.common.utills.users.oauth.naver.NaverUtil;
import org.maping.maping.external.oauth.naver.dto.response.NaverUserInfoResponse;
import org.maping.maping.repository.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.maping.maping.api.auth.dto.response.OAuthLoginResponse;
import org.maping.maping.common.enums.expection.ErrorCode;
import org.maping.maping.common.utills.jwt.JWTUtill;
import org.maping.maping.common.utills.jwt.dto.JwtDto;
import org.maping.maping.exceptions.CustomException;
import org.maping.maping.model.user.LocalJpaEntity;
import org.maping.maping.model.user.UserInfoJpaEntity;
import org.maping.maping.repository.user.LocalRepository;
import org.maping.maping.common.utills.users.oauth.google.GoogleUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
