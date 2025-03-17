package org.maping.maping.api.social.service;

import org.maping.maping.api.social.dto.request.AddFavoriteRequest;
import org.maping.maping.api.social.dto.response.FavoriteCharacterResponse;
import org.maping.maping.model.social.FavoriteJpaEntity;
import org.maping.maping.model.user.UserInfoJpaEntity;

import java.util.List;

public interface SocialService {
    void addFavorite(Long userId, AddFavoriteRequest request);
    List<FavoriteCharacterResponse> getFavorites(Long userId);
}
