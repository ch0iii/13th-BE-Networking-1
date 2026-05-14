package cotato.backend.domain.application.exception;

import cotato.backend.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ApplicationErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다.");
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "지원서를 찾을 수 없습니다."),

    INVALID_PERIOD(HttpStatus.BAD_REQUEST, "지원 기수는 1 이상의 정수여야 합니다."),
    INVALID_ABILITY(HttpStatus.BAD_REQUEST, "실력은 0 이상 10 이하의 정수여야 합니다."),
    INVALID_PASSION(HttpStatus.BAD_REQUEST, "열정은 0 이상 10 이하의 정수여야 합니다."),

    ONLY_STAFF_CAN_LIKE(HttpStatus.FORBIDDEN, "운영진만 서류 관심 표시를 할 수 있습니다."),

    ALREADY_SUBMITTED_APPLICATION(HttpStatus.CONFLICT, "이미 해당 기수에 지원서를 제출했습니다."),

    private final HttpStatus httpStatus;
    private final String message;
}



