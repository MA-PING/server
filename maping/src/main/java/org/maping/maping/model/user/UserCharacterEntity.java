package org.maping.maping.model.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CHARACTER_TB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private int characterId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(nullable = false)
    private int ocid;

    @Column(name = "character_name", length = 100, nullable = false)
    private String character_Name;

    @Column(name = "character_level", nullable = false)
    private int character_Level;

    @Column(length = 100)
    private String nickname;

    @Column(name = "world_name", length = 100)
    private String world_Name;

    @Column(name = "character_class", length = 100)
    private String character_Class;

    @Column(length = 255)
    private String image;

    @Column(name = "world_img", length = 255)
    private String world_Img;

    @Column(length = 100)
    private String guild;
}
