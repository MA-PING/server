package org.maping.maping.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_id")
    private Long apiId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserInfoJpaEntity userInfo;

    @Column(name = "user_api_info", nullable = false)
    private String userApiInfo;

    @Version
    @NotNull
    @Column(name = "version")
    private Long version;
}