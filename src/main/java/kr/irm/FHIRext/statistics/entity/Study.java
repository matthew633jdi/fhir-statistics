package kr.irm.FHIRext.statistics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "metadata_studyuid")
@SequenceGenerator(
        name = "STUDY_SEQ_GENERATOR",
        sequenceName = "metadata_studyuid$SQ",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor
public class Study {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STUDY_SEQ_GENERATOR")
    @Column(name = "meta_stduid_key")
    private Long id;

    @Column(name = "study_uid")
    private String studyUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metadata_id", nullable = false)
    private Metadata metadata;

}
