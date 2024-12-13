package kr.irm.FHIRext.statistics.repository;

import kr.irm.FHIRext.statistics.config.QueryDslConfig;
import kr.irm.FHIRext.statistics.dto.RequestVO;
import kr.irm.FHIRext.statistics.entity.Metadata;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QueryDslConfig.class)
class MetadataRepositoryTest {

    @Autowired
    private MetadataRepository metadataRepository;

    @BeforeEach
    void setUp() {
        Metadata metadata = Metadata.builder()
                .encounterId("enc_1")
                .encounterStartdate(OffsetDateTime.of(LocalDate.of(2024, 12, 23), LocalTime.MIN, ZoneOffset.UTC))
                .encounterEnddate(OffsetDateTime.of(LocalDate.of(2024, 12, 23), LocalTime.MIN, ZoneOffset.UTC))
                .createdDttm(OffsetDateTime.now())
                .organizationId("org_1")
                .organizationName("org_name")
                .patientId("pat_1")
                .patientName("pat_name")
                .patientSex("male")
                .patientBirthdate(OffsetDateTime.of(LocalDate.of(1999, 10, 23), LocalTime.MIN, ZoneOffset.UTC))
                .build();

        Metadata metadata1 = Metadata.builder()
                .encounterId("enc_2")
                .encounterStartdate(OffsetDateTime.of(LocalDate.of(2024, 11, 1), LocalTime.MIN, ZoneOffset.UTC))
                .encounterEnddate(OffsetDateTime.of(LocalDate.of(2024, 12, 21), LocalTime.MIN, ZoneOffset.UTC))
                .createdDttm(OffsetDateTime.now())
                .organizationId("org_2")
                .organizationName("org_name2")
                .patientId("pat_2")
                .patientName("pat_name2")
                .patientSex("female")
                .patientBirthdate(OffsetDateTime.of(LocalDate.of(1997, 2, 21), LocalTime.MIN, ZoneOffset.UTC))
                .build();

        Metadata metadata2 = Metadata.builder()
                .encounterId("enc_3")
                .encounterStartdate(OffsetDateTime.of(LocalDate.of(2024, 12, 1), LocalTime.MIN, ZoneOffset.UTC))
                .encounterEnddate(OffsetDateTime.of(LocalDate.of(2024, 12, 3), LocalTime.MIN, ZoneOffset.UTC))
                .createdDttm(OffsetDateTime.now())
                .organizationId("org_1")
                .organizationName("org_name")
                .patientId("pat_1")
                .patientName("pat_name")
                .patientSex("male")
                .patientBirthdate(OffsetDateTime.of(LocalDate.of(1999, 10, 23), LocalTime.MIN, ZoneOffset.UTC))
                .build();

        metadataRepository.saveAll(List.of(metadata, metadata1, metadata2));
    }

    @Test
    @DisplayName("수진 ID 조회")
    void findByEncounterId() {
        // given
        RequestVO request = RequestVO.builder().encounterId("enc_1").build();
        // when
        Page<Metadata> response = metadataRepository.findByCriteria(request, PageRequest.of(0, Integer.MAX_VALUE));
        // when
        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getContent().get(0).getEncounterId()).isEqualTo("enc_1");
    }

    @Test
    @DisplayName("기관명 조회")
    void findByOrgName() {
        // given
        RequestVO request = RequestVO.builder().organizationName("org_name").build();
        // when
        Page<Metadata> response = metadataRepository.findByCriteria(request, PageRequest.of(0, Integer.MAX_VALUE));
        // when
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, response.getTotalElements()),
                () -> Assertions.assertEquals("org_name", response.getContent().get(0).getOrganizationName())
        );
    }

    @Test
    @DisplayName("환자 ID 조회")
    void methodName() {
        // given
        RequestVO request = RequestVO.builder()
                .patientId("pat_1")
                .build();
        // when
        Page<Metadata> response = metadataRepository.findByCriteria(request, PageRequest.of(0, Integer.MAX_VALUE));
        // when
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, response.getTotalElements()),
                () -> Assertions.assertEquals("pat_name", response.getContent().get(0).getPatientName())
        );
    }

    @Test
    @DisplayName("방문일 조회")
    void findByEncounterDate() {
        // given
        RequestVO request = RequestVO.builder()
                .date(LocalDate.of(2024, 12, 23))
                .build();
        // when
        Page<Metadata> response = metadataRepository.findByCriteria(request, PageRequest.of(0, Integer.MAX_VALUE));
        // when
        assertThat(response.getTotalElements()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("방문 범위 조회")
    void findByEncounterPeriod() {
        // given
        RequestVO request = RequestVO.builder()
                .startDate(LocalDate.of(2024, 11, 20))
                .endDate(LocalDate.of(2024, 12, 1))
                .build();
        // when
        Page<Metadata> response = metadataRepository.findByCriteria(request, PageRequest.of(0, Integer.MAX_VALUE));
        // when
        assertThat(response.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("성별 조회")
    void findByGender() {
        // given
        RequestVO request = RequestVO.builder()
                .patientSex("male")
                .build();
        // when
        Page<Metadata> response = metadataRepository.findByCriteria(request, PageRequest.of(0, Integer.MAX_VALUE));
        // when
        assertThat(response.getContent().stream().findAny().filter(x -> x.getPatientSex().equals("female")).isPresent()).isFalse();
    }

    @Disabled
    @Test
    @DisplayName("연령대 조회")
    void findByAgeGroup() {
        // given
        
        // when
        
        // when
        
    }

}