package org.maping.maping.repository.search;
import org.checkerframework.checker.units.qual.A;
import org.maping.maping.model.search.CharacterSearchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CharacterSearchRepository extends JpaRepository<CharacterSearchJpaEntity, Long> {

    Optional<CharacterSearchJpaEntity> findByCharacterName(String characterName);
}
