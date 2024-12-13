package kr.irm.FHIRext.statistics.util;

import kr.irm.FHIRext.statistics.dto.RequestVO;
import kr.irm.FHIRext.statistics.dto.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.StringJoiner;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(public * kr.irm.FHIRext.statistics.web.StatisticsController.getMetas(..))")
    public void controller() {}

    @Pointcut("execution(public * kr.irm.FHIRext.statistics.service.StatisticsService.*(..))")
    public void service() {}

    @Around("controller() && args(request, ..)")
    public Object controllerAdvice(ProceedingJoinPoint joinPoint, RequestVO request) throws Throwable {
        Signature signature = joinPoint.getSignature();

        log.info("fhirext-statistics: received REST api");
        printMethodSignature(signature);

        log.info("fhirext-statistics: received REST api. body={}", request.toString());
        Object result = joinPoint.proceed();

        Page<ResponseVO> response = (Page<ResponseVO>) result;
        log.info("fhirext-statistics: completed REST api");
        log.info("fhirext-statistics: completed REST api. count={}", response.getTotalElements());

        return result;
    }

    @AfterThrowing(value = "service()", throwing = "exception")
    public void afterThrowing(Exception exception) {
        log.error("fhirext-statistics: error occurred while selecting service. {}", exception.getMessage());
    }

    private void printMethodSignature(Signature signature) {
        String className = signature.getDeclaringType().getSimpleName();
        String parameters = getParameters(signature);
        String methodName = signature.getName();
        log.info("fhirext-statistics: {}.{}()", className, methodName);
        log.debug("fhirext-statistics: {}.{}() parameters=[{}]", className, methodName, parameters);
    }

    private String getParameters(Signature signature) {
        StringJoiner joiner = new StringJoiner(",");
        Method method = ((MethodSignature) signature).getMethod();
        for (Parameter parameter : method.getParameters()) {
            joiner.add(parameter.getType().getSimpleName());
        }
        return joiner.toString();
    }

}