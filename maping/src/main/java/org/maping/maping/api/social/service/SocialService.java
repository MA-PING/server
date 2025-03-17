package org.maping.maping.api.social.service;

import org.maping.maping.api.social.dto.request.AddFavoriteRequest;

public interface SocialService {
    void addFavorite(Long userId, AddFavoriteRequest request);
}
