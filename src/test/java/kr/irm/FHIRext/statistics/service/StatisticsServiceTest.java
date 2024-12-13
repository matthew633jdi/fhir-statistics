package kr.irm.FHIRext.statistics.service;

import kr.irm.FHIRext.statistics.dto.RequestVO;
import kr.irm.FHIRext.statistics.dto.ResponseVO;
import kr.irm.FHIRext.statistics.entity.Metadata;
import kr.irm.FHIRext.statistics.repository.MetadataRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    private MetadataRepository metadataRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    @DisplayName("환자 기반 조회")
    void findByPatientName() {
        // given
        String patientId = "1";
        RequestVO request = RequestVO.builder().patientId(patientId).build();
        Metadata metadata = Metadata.builder()
                .patientId(patientId)
                .patientName("pat_name")
                .encounterStartdate(OffsetDateTime.now())
                .encounterEnddate(OffsetDateTime.now())
                .patientBirthdate(OffsetDateTime.now())
                .build();

        when(metadataRepository.findByCriteria(request, PageRequest.of(0, 10))).thenReturn(new PageImpl<>(List.of(metadata), PageRequest.of(0, 10), 1));
        // when
        Page<ResponseVO> response = statisticsService.findAll(request, PageRequest.of(0, 10));
        // when
        response.get().forEach(System.out::println);
        assertThat(response.getContent().get(0).patientName()).isEqualTo("pat_name");
    }

}