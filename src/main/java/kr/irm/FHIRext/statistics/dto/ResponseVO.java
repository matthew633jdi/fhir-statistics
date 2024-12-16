package kr.irm.FHIRext.statistics.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import kr.irm.FHIRext.statistics.entity.Metadata;
import kr.irm.FHIRext.statistics.entity.Study;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ResponseVO(
        String encounterId,
        LocalDate encounterStartdate,
        LocalDate encounterEnddate,
        String organizationId,
        String organizationName,
        String patientId,
        String patientName,
        String patientSex,
        LocalDate patientBirthdate,
        OffsetDateTime createDttm,
        OffsetDateTime updateDttm,
        List<String> studyUidList) {

    public static ResponseVO of(Metadata metadata) {
        return new ResponseVO(
                metadata.getEncounterId(),
                metadata.getEncounterStartdate().toLocalDate(),
                metadata.getEncounterEnddate().toLocalDate(),
                metadata.getOrganizationId(),
                metadata.getOrganizationName(),
                metadata.getPatientId(),
                metadata.getPatientName(),
                metadata.getPatientSex(),
                metadata.getPatientBirthdate().toLocalDate(),
                metadata.getCreatedDttm(),
                metadata.getUpdatedDttm(),
                metadata.getStudyList().stream().map(Study::getStudyUid).toList());
    }
}
