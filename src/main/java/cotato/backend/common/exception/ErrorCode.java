package cotato.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	//400
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", "COMMON-001"),
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "요청 파라미터가 잘못되었습니다.", "COMMON-002"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없습니다.", "COMMON-003"),

	// User
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다.", "USER-001"),
	NOT_APPLICANT(HttpStatus.BAD_REQUEST, "해당 회원은 지원자가 아닙니다.", "USER-002"),
	NOT_ADMIN(HttpStatus.BAD_REQUEST, "해당 회원은 운영진이 아닙니다.", "USER-003"),
	// Application
	APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "지원서를 찾을 수 없습니다.", "APPLICATION-001"),
	ALREADY_SUBMITTED_APPLICATION(HttpStatus.BAD_REQUEST, "이미 해당 기수에 지원서를 제출했습니다.", "APPLICATION-002"),

	// 403
	ONLY_ADMIN_CAN_LIKE(HttpStatus.FORBIDDEN, "운영진만 서류 관심 표시를 할 수 있습니다.", "APPLICATION-004"),

	//500
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 에러가 발생하였습니다.", "COMMON-004"),
	;

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}