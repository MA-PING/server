package org.maping.maping.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(nullable = false)
    private String email;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column
    private String iconic;

    @Column(name = "main_character")
    private String mainCharacter;


    @CreationTimestamp // 생성일자를 자동으로 관리
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private UserLocalJpaEntity local; // Local을 LocalJpaEntity로 수정

    //@OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
    //private NaverJpaEntity naver; // Local을 LocalJpaEntity로 수정

    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private UserApiJpaEntity userApi;

    @OneToMany(mappedBy = "user")
    private Set<UserOcidJpaEntity> userOcidTbs = new LinkedHashSet<>();



}




