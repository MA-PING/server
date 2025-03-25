package org.maping.maping.repository.user;

import org.maping.maping.model.user.UserCharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCharacterRepository extends JpaRepository<UserCharacterEntity, Integer> {


    List<UserCharacterEntity> findByUserId(int userId);
}