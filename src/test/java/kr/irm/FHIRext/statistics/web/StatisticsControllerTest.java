package kr.irm.FHIRext.statistics.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.irm.FHIRext.statistics.dto.RequestVO;
import kr.irm.FHIRext.statistics.dto.ResponseVO;
import kr.irm.FHIRext.statistics.entity.Metadata;
import kr.irm.FHIRext.statistics.service.StatisticsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.*;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private static StatisticsService statisticsService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("환자 성별 조회")
    void basic() throws Exception {
        // given
        Metadata metadata = Metadata.builder()
                .patientBirthdate(OffsetDateTime.now())
                .encounterStartdate(OffsetDateTime.now())
                .encounterEnddate(OffsetDateTime.now())
                .patientName("pat_name")
                .patientSex("male")
                .patientBirthdate(OffsetDateTime.of(LocalDate.of(1999, 9, 9), LocalTime.MIN, ZoneOffset.UTC))
                .build();

        ResponseVO response = ResponseVO.of(metadata);
        PageImpl<ResponseVO> page = new PageImpl<>(List.of(response));

        given(statisticsService.findAll(any(RequestVO.class), any(Pageable.class))).willReturn(page);

        RequestVO request = RequestVO.builder()
                .patientSex("male")
                .build();

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.page.size").value("1"))
                .andExpect(jsonPath("$.content[0].patient_sex").value("male"))
                .andExpect(jsonPath("$.content[0].patient_birthdate").value("1999-09-09"));
    }

}