package org.maping.maping.model.social;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CHARACTER_SEARCH_TB")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CharacterSearchJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Long characterId;

    @Column(nullable = false, length = 150)
    private String ocid;

    @Column(nullable = false, length = 100)
    private String characterName;

    @Column(nullable = false)
    private Long characterLevel;

    @Column(nullable = false, length = 100)
    private String worldName;

    @Column(nullable = false, length = 100)
    private String characterClass;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false, length = 255)
    private String worldImg;

    @Column(length = 128)
    private String guild;

    @Column(nullable = false, length = 255)
    private String jaso;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
