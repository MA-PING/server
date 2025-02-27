package org.maping.maping.repository.user;
import org.maping.maping.model.user.UserApiJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApiRepository extends JpaRepository<UserApiJpaEntity, Long> {

}
