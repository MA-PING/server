package org.maping.maping.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.maping.maping.model.ai.AiHistoryJpaEntity;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "USER_INFO_TB")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserInfoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "iconic")
    private String iconic;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @OneToOne(mappedBy = "userInfoTb")
    private UserApiJpaEntity userApiTb;

    @OneToMany(mappedBy = "user")
    private Set<AiHistoryJpaEntity> aiHistoryTbs = new LinkedHashSet<>();

}
