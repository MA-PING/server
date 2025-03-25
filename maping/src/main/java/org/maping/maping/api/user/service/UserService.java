package org.maping.maping.api.user.service;

import org.maping.maping.api.user.dto.request.SaveApiRequest;
import org.maping.maping.api.user.dto.request.SaveMainCharacterRequest;

public interface UserService {
    void saveApi (Long UserId, SaveApiRequest saveApiRequest);
    void updateApi (Long UserId, SaveApiRequest saveApiRequest);
    void original(Long userId, SaveMainCharacterRequest saveMainCharacterRequest);
}
