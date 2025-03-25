package org.maping.maping.repository.user;


import org.maping.maping.model.user.UserOcidJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOcidRepository extends JpaRepository<UserOcidJpaEntity, Long> {
}
