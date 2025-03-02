package org.maping.maping.api.user.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.maping.maping.api.user.dto.response.UserInfoResponse;
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
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final String NICKNAME_REGEX = "^[가-힣a-zA-Z0-9]{2,10}$";
    private static final Pattern NICKNAME_PATTERN = Pattern.compile(NICKNAME_REGEX);

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        UserInfoJpaEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다."));

        return new UserInfoResponse(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getUserApi() != null ? user.getUserApi().getUserApiInfo() : null
        );
    }

    @Transactional
    @Override
    public void updateNickname(Long userId, String newNickname) {
        // 닉네임 유효성 검사
        if (newNickname == null || !NICKNAME_PATTERN.matcher(newNickname).matches()) {
            throw new CustomException(ErrorCode.BadRequest, "닉네임은 한글, 영어, 숫자로만 2자 이상 10자 이하로 입력해야 합니다.");
        }

        // 사용자 조회
        UserInfoJpaEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다."));

        // 닉네임 중복 체크
        if (userRepository.existsByuserName(newNickname)) {
            throw new CustomException(ErrorCode.BadRequest, "이미 사용 중인 닉네임입니다.");
        }

        // 닉네임 업데이트
        user.setUserName(newNickname);
        userRepository.save(user);
    }

}
