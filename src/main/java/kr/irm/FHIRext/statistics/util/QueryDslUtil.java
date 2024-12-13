package kr.irm.FHIRext.statistics.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class QueryDslUtil {

    public static BooleanExpression eqString(StringPath objectString, String paramString) {
        return paramString != null ? objectString.eq(paramString) : null;
    }

    public static BooleanExpression likeString(StringPath objectString, String paramString) {
        return paramString != null ? objectString.likeIgnoreCase(String.format("%%%s%%", paramString)) : null;
    }

    public static BooleanExpression eqDateForDttm(DateTimePath<OffsetDateTime> objectDttm, LocalDate paramDate) {
        return paramDate != null ? objectDttm.between(paramDate.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime(), paramDate.atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toOffsetDateTime()) : null;
    }

    public static BooleanExpression goeDateForDttm(DateTimePath<OffsetDateTime> objectDttm, LocalDate paramDate) {
        return paramDate != null ? objectDttm.goe(paramDate.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime()) : null;
    }

    public static BooleanExpression loeDateForDttm(DateTimePath<OffsetDateTime> objectDttm, LocalDate paramDate) {
        return paramDate != null ? objectDttm.loe(paramDate.atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toOffsetDateTime()) : null;
    }

    public static BooleanExpression eqDttm(DateTimePath<LocalDateTime> objectDttm, LocalDateTime paramDttm) {
        return paramDttm != null ? objectDttm.eq(paramDttm) : null;
    }

    public static BooleanExpression goeDttm(DateTimePath<LocalDateTime> objectDttm, LocalDateTime paramDttm) {
        return paramDttm != null ? objectDttm.goe(paramDttm) : null;
    }

    public static BooleanExpression loeDttm(DateTimePath<LocalDateTime> objectDttm, LocalDateTime paramDttm) {
        return paramDttm != null ? objectDttm.loe(paramDttm) : null;
    }

    public static List<OrderSpecifier> sort(EntityPathBase<?> objectClass, Pageable pageable) {
        Sort sort = pageable.getSort();
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = BaseUtil.fromSnaketoCamel(order.getProperty());
            PathBuilder orderByExpression = new PathBuilder(objectClass.getType(), objectClass.getMetadata());
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        return orders;
    }
}
