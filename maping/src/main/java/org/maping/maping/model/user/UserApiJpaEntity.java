package org.maping.maping.model.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USER_API_TB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiJpaEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserInfoJpaEntity userInfo;

    @Column(name = "user_api_info", nullable = false)
    private String userApiInfo;
}
