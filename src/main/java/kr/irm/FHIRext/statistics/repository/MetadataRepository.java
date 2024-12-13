package kr.irm.FHIRext.statistics.repository;

import kr.irm.FHIRext.statistics.entity.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataRepository extends JpaRepository<Metadata, Long>, MetadataCustomRepository {
}
