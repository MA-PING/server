package org.maping.maping.model.ai;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "NOTICE_TB")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NoticeJpaEntity {
    @Id
    @Column(name = "notice_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "notice_part", nullable = false)
    private String noticePart;

    @Size(max = 255)
    @NotNull
    @Column(name = "notice_title", nullable = false)
    private String noticeTitle;

    @Size(max = 255)
    @NotNull
    @Column(name = "notice_content", nullable = false)
    private String noticeContent;

    @NotNull
    @Column(name = "notice_date", nullable = false)
    private Instant noticeDate;

    @NotNull
    @Lob
    @Column(name = "notice_summary", nullable = false)
    private String noticeSummary;

    @Size(max = 255)
    @NotNull
    @Column(name = "notice_url", nullable = false)
    private String noticeUrl;

    @Size(max = 15)
    @Column(name = "version", length = 15)
    private String version;

}
