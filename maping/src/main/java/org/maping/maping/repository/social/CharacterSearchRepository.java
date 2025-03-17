package org.maping.maping.repository.social;


import org.maping.maping.model.social.CharacterSearchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterSearchRepository extends JpaRepository<CharacterSearchJpaEntity, Long> {
}
