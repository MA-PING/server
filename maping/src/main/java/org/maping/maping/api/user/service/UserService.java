package org.maping.maping.api.user.service;

import org.maping.maping.api.auth.dto.request.PasswordRequest;
import org.maping.maping.api.user.dto.response.UserInfoResponse;
import org.maping.maping.model.user.UserInfoJpaEntity;
import org.maping.maping.api.user.dto.request.SaveApiRequest;
import org.maping.maping.api.user.dto.request.SaveMainCharacterRequest;
public interface UserService {

    void deleteUser(Long userId);

    UserInfoResponse getUserInfo(Long userId);

    void updateNickname(Long userId, String newNickname);

    void updatePassword(Long userId, PasswordRequest passwordRequest);

    void saveApi (Long UserId, SaveApiRequest saveApiRequest);

    void updateApi (Long UserId, SaveApiRequest saveApiRequest);
    
    void original(Long userId, SaveMainCharacterRequest saveMainCharacterRequest);
}
