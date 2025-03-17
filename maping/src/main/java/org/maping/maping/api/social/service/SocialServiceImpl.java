package org.maping.maping.api.social.service;

import org.maping.maping.api.social.dto.request.AddFavoriteRequest;
import org.maping.maping.model.social.CharacterSearchJpaEntity;
import org.maping.maping.model.social.FavoriteJpaEntity;
import org.maping.maping.model.user.UserInfoJpaEntity;
import org.maping.maping.repository.social.CharacterSearchRepository;
import org.maping.maping.repository.social.FavoriteRepository;
import org.maping.maping.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SocialServiceImpl implements  SocialService {
    private  final UserRepository userRepository;
    private  final FavoriteRepository favoriteRepository;
    private  final CharacterSearchRepository characterSearchRepository;

    public void addFavorite(Long userId, AddFavoriteRequest request) {
        UserInfoJpaEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        CharacterSearchJpaEntity character = characterSearchRepository.findById(request.getCharacterId())
                .orElseThrow(() -> new IllegalArgumentException("해당 캐릭터가 존재하지 않습니다."));

        FavoriteJpaEntity favorite = FavoriteJpaEntity.builder()
                .user(user)
                .character(character)
                .build();

        favoriteRepository.save(favorite);
    }
}
