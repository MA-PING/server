package org.maping.maping.repository.user;
import org.maping.maping.model.user.UserApiJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.maping.maping.model.user.UserInfoJpaEntity;
import java.util.Optional;

@Repository
public interface UserApiRepository extends JpaRepository<UserApiJpaEntity, Long> {

    Optional<UserApiJpaEntity> findById(Long userId);

    UserApiJpaEntity findByUserInfo(UserInfoJpaEntity userInfo);

}
