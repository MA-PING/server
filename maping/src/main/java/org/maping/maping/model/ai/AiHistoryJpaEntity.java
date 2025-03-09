package org.maping.maping.model.ai;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.maping.maping.model.user.UserInfoJpaEntity;

import java.time.Instant;

@Entity
@Table(name = "AI_HISTORY_TB")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AiHistoryJpaEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInfoJpaEntity user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "ai_date_time", nullable = false)
    private Instant aiDateTime;

    @Size(max = 255)
    @Column(name = "topic")
    private String topic;

    @Lob
    @Column(name = "content")
    private String content;

}
