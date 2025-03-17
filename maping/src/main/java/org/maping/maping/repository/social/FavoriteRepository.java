package org.maping.maping.repository.social;

import org.maping.maping.model.social.FavoriteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoriteJpaEntity, Long> {

}
