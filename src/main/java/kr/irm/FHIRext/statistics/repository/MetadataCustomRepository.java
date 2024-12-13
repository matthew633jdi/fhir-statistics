package kr.irm.FHIRext.statistics.repository;

import kr.irm.FHIRext.statistics.dto.RequestVO;
import kr.irm.FHIRext.statistics.entity.Metadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MetadataCustomRepository {

    Page<Metadata> findByCriteria(RequestVO criteria, Pageable pageable);
}
