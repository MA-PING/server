package org.maping.maping.model.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "USER_OCID_TB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOcidJpaEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInfoJpaEntity user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ocid", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "user_ocid", nullable = false)
    private String userOcid;

    @NotNull
    @Column(name = "main", nullable = false)
    private Boolean main = false;

}
