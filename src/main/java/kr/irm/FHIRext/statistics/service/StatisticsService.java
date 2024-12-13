package kr.irm.FHIRext.statistics.service;

import kr.irm.FHIRext.statistics.dto.RequestVO;
import kr.irm.FHIRext.statistics.dto.ResponseVO;
import kr.irm.FHIRext.statistics.repository.MetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final MetadataRepository metadataRepository;

    public Page<ResponseVO> findAll(RequestVO requestVO, Pageable pageable) {
        return metadataRepository.findByCriteria(requestVO, pageable).map(ResponseVO::of);
    }
}
