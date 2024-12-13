package kr.irm.FHIRext.statistics.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.PastOrPresent;
import kr.irm.FHIRext.statistics.dto.base.DateBefore;
import kr.irm.FHIRext.statistics.dto.base.GenderType;
import kr.irm.FHIRext.statistics.dto.base.ValidDateRange;
import kr.irm.FHIRext.statistics.dto.base.ValueOfEnum;
import lombok.Builder;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@DateBefore(start = "startDate", end = "endDate", message = "start_date should be before end_date.")
@ValidDateRange(startRange = "startDate", endRange = "endDate", date = "date", message = "When there is a start_date or end_date, there must be no date, and when there is a date, there must be no start_date or end_date.")
public record RequestVO(

        String organizationId,

        String organizationName,

        String patientId,

        String patientName,

        @ValueOfEnum(enumClass = GenderType.class, message = "value can only be entered as 'male' or 'female'.")
        String patientSex,

        @PastOrPresent(message = "value must be present or past.")
        LocalDate patientBirthdate,

        String encounterId,

        @PastOrPresent(message = "value must be present or past.")
        LocalDate startDate,

        @PastOrPresent(message = "value must be present or past.")
        LocalDate endDate,

        @PastOrPresent(message = "value must be present or past.")
        LocalDate date,

        Boolean hasStudyuid) {

}
