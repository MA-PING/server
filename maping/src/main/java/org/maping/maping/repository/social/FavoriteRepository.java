package org.maping.maping.repository.social;

import org.maping.maping.model.social.CharacterSearchJpaEntity;
import org.maping.maping.model.social.FavoriteJpaEntity;
import org.maping.maping.model.user.UserInfoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;  // Optional 클래스 임포트


public interface FavoriteRepository extends JpaRepository<FavoriteJpaEntity, Long> {

    List<FavoriteJpaEntity> findAllByUser(UserInfoJpaEntity user);
    Optional<FavoriteJpaEntity> findByUserAndCharacter(UserInfoJpaEntity user, CharacterSearchJpaEntity character);

}
