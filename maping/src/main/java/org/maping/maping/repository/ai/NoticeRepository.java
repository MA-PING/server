package org.maping.maping.repository.ai;
import org.maping.maping.model.ai.NoticeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeJpaEntity, Long> {
}
