package org.maping.maping.api.user.service;

import org.maping.maping.api.user.dto.request.SaveApiRequest;
import org.maping.maping.api.user.dto.request.SaveMainCharacterRequest;
import org.maping.maping.model.user.UserApiJpaEntity;
import org.maping.maping.model.user.UserInfoJpaEntity;
import org.maping.maping.repository.user.UserApiRepository;
import org.maping.maping.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.maping.maping.common.enums.expection.ErrorCode;
import org.maping.maping.exceptions.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService{
    private final UserRepository userRepository;
    private final UserApiRepository userApiRepository;
    @Transactional
    @Override
    public void saveApi(Long userId, SaveApiRequest saveApiRequest) {
        UserInfoJpaEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다."));

        UserApiJpaEntity userApi = user.getUserApi();

        if (userApi == null) {
            userApi = UserApiJpaEntity.builder()
                    .userInfo(user)
                    .version(1L)
                    .userApiInfo(saveApiRequest.getApiKey())
                    .build();

            // 양방향 매핑 세팅
            user.setUserApi(userApi);
        } else {
            userApi.setUserApiInfo(saveApiRequest.getApiKey());
        }

        // 반드시 직접 저장
        userApiRepository.save(userApi);
    }


    @Override
    public void updateApi(Long userId, SaveApiRequest saveApiRequest) {
        UserInfoJpaEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다."));

        UserApiJpaEntity userApi = userApiRepository.findByUserInfo(user);

        if (userApi == null) {
            userApi = UserApiJpaEntity.builder()
                    .userInfo(user)
                    .version(userApi.getVersion() + 1L)
                    .userApiInfo(saveApiRequest.getApiKey())
                    .build();

            // 양방향 매핑 세팅
            user.setUserApi(userApi);
        } else {
            userApi.setUserApiInfo(saveApiRequest.getApiKey());
        }

        // 반드시 직접 저장
        userApiRepository.save(userApi);
    }


    @Transactional
    @Override
    public void original(Long userId, SaveMainCharacterRequest saveMainCharacterRequest) {
        UserInfoJpaEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다."));

        user.setMainCharacter(saveMainCharacterRequest.getCharacterName());

        userRepository.save(user);
    }



}
