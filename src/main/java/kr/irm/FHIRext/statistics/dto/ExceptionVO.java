package kr.irm.FHIRext.statistics.dto;

public record ExceptionVO(String code, String message) {

    public static ExceptionVO of(String code, String message) {
        return new ExceptionVO(code, message);
    }
}
