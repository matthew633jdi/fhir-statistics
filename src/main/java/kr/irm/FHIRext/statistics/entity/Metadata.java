package kr.irm.FHIRext.statistics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Metadata {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metadata_id")
    private Long metadataId;

    @Column(name = "organization_id")
    private String organizationId;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "patient_sex")
    private String patientSex;

    @Column(name = "patient_birthdate")
    private OffsetDateTime patientBirthdate;

    @Column(name = "encounter_id")
    private String encounterId;

    @Column(name = "encounter_startdate")
    private OffsetDateTime encounterStartdate;

    @Column(name = "encounter_enddate")
    private OffsetDateTime encounterEnddate;

    @Column(name = "created_dttm")
    private OffsetDateTime createdDttm;

    @Column(name = "updated_dttm")
    private OffsetDateTime updatedDttm;

    @Builder.Default
    @OneToMany(mappedBy = "metadata")
    private List<Study> studyList = new ArrayList<>();

}
