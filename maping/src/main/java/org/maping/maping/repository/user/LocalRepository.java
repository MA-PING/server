package org.maping.maping.repository.user;

import org.maping.maping.model.user.LocalJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<LocalJpaEntity, Long> {

}
