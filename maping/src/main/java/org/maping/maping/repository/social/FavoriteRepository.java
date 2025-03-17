package org.maping.maping.repository.social;

import org.maping.maping.model.social.FavoriteJpaEntity;
import org.maping.maping.model.user.UserInfoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteJpaEntity, Long> {

    List<FavoriteJpaEntity> findAllByUser(UserInfoJpaEntity user);

}
