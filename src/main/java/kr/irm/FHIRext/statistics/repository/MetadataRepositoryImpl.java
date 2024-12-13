package kr.irm.FHIRext.statistics.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.irm.FHIRext.statistics.dto.RequestVO;
import kr.irm.FHIRext.statistics.entity.Metadata;
import kr.irm.FHIRext.statistics.entity.QStudy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static kr.irm.FHIRext.statistics.entity.QMetadata.metadata;
import static kr.irm.FHIRext.statistics.entity.QStudy.study;
import static kr.irm.FHIRext.statistics.util.QueryDslUtil.*;

@RequiredArgsConstructor
public class MetadataRepositoryImpl implements MetadataCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Metadata> findByCriteria(RequestVO criteria, Pageable pageable) {
        List<Metadata> metadataList = jpaQueryFactory
                .selectFrom(metadata)
                .leftJoin(metadata.studyList, study)
                .fetchJoin()
                .where(
                        eqString(metadata.organizationId, criteria.organizationId()),
                        eqString(metadata.organizationName, criteria.organizationName()),
                        eqString(metadata.patientId, criteria.patientId()),
                        eqString(metadata.patientName, criteria.patientName()),
                        eqString(metadata.patientSex, criteria.patientSex()),
                        eqDateForDttm(metadata.patientBirthdate, criteria.patientBirthdate()),
                        eqString(metadata.encounterId, criteria.encounterId()),
                        goeDateForDttm(metadata.encounterEnddate, criteria.startDate()),
                        loeDateForDttm(metadata.encounterStartdate, criteria.endDate()),
                        eqDateForDttm(metadata.encounterEnddate, criteria.date()),
                        includeStudy(criteria.hasStudyuid())
                )
                .distinct()
                .orderBy(sort(metadata, pageable).stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(metadata.metadataId.countDistinct())
                .from(metadata)
                .leftJoin(metadata.studyList, study)
                .where(
                        eqString(metadata.organizationId, criteria.organizationId()),
                        eqString(metadata.organizationName, criteria.organizationName()),
                        eqString(metadata.patientId, criteria.patientId()),
                        eqString(metadata.patientName, criteria.patientName()),
                        eqString(metadata.patientSex, criteria.patientSex()),
                        eqDateForDttm(metadata.patientBirthdate, criteria.patientBirthdate()),
                        eqString(metadata.encounterId, criteria.encounterId()),
                        goeDateForDttm(metadata.encounterEnddate, criteria.startDate()),
                        loeDateForDttm(metadata.encounterStartdate, criteria.endDate()),
                        eqDateForDttm(metadata.encounterEnddate, criteria.date()),
                        includeStudy(criteria.hasStudyuid())
                )
                .fetchFirst();

        return new PageImpl<>(metadataList, pageable, count);
    }

    private BooleanExpression includeStudy(Boolean flag) {
        if (flag == null) return null;

        return flag ? study.id.isNotNull() : study.id.isNull();
    }
}
