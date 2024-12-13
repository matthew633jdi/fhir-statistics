package kr.irm.FHIRext.statistics.web;

import kr.irm.FHIRext.statistics.dto.RequestVO;
import kr.irm.FHIRext.statistics.dto.ResponseVO;
import kr.irm.FHIRext.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PostMapping
    public Page<ResponseVO> getMetas(@Validated @RequestBody RequestVO request, Pageable pageable) {
        return statisticsService.findAll(request, pageable);
    }

}
