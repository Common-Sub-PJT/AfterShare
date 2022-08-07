package com.ssafy.common.exception.enums;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "002"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "003"),

    BAD_REQUEST_DATE(HttpStatus.BAD_REQUEST, "004", "잘못된 날짜 혹은 시간 혹은 요일 형식이 요청되었습니다."),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "101", "해당 유저를 찾을 수 없습니다."),
    UNAUTHORIZED_USER_PASSWORD(HttpStatus.UNAUTHORIZED, "104", "잘못된 비밀번호입니다."),
    CONFLICT_UESR(HttpStatus.CONFLICT, "105", "이미 해당 아이디가 존재합니다."),

    NOT_FOUND_STUDY(HttpStatus.NOT_FOUND, "201", "해당 스터디를 찾을 수 없습니다."),
    BAE_REQUEST_STUDY(HttpStatus.BAD_REQUEST, "203", "스터디에 카테고리를 반드시 지정해야 합니다."),
    UNAUTHORIZED_STUDY(HttpStatus.UNAUTHORIZED, "204", "현재 로그인한 사용자는 해당 요청에 대한 권한이 없습니다."),

    NOT_FOUND_STUDY_MEMBER(HttpStatus.NOT_FOUND, "301", "해당 유저는 해당 스터디의 멤버가 아닙니다."),
    CONFLICT_STUDY_MEMBER(HttpStatus.CONFLICT, "302", "이미 가입된 유저입니다."),
    UNAUTHORIZED_STUDY_MEMBER(HttpStatus.UNAUTHORIZED, "304", "현재 로그인한 사용자는 해당 요청에 대한 권한이 없습니다."),
    CONFLICT_STUDY_MASTER_DELETE(HttpStatus.CONFLICT, "305", "스터디장은 스터디 탈퇴를 할 수 없습니다. 스터디 삭제를 하거나 스터디장을 위임 후 탈퇴해주세요."),
    UNAUTHORIZED_STUDY_MEMBER_MAX(HttpStatus.UNAUTHORIZED, "306", "스터디가 꽉 찼습니다."),

    NOT_FOUND_STUDY_BOARD(HttpStatus.NOT_FOUND, "401", "해당 스터디 보드 게시글을 찾을 수 없습니다."),
    CONFLICT_STUDY_BOARD(HttpStatus.CONFLICT, "402", "가능한 스터디 보드 게시글의 갯수를 초과하였습니다."),
    BAD_REQUEST_STUDY_BOARD(HttpStatus.BAD_REQUEST, "403", "해당 게시글은 주어진 study_id에 속한 글이 아닙니다."),
    UNAUTHORIZED_STUDY_BOARD(HttpStatus.UNAUTHORIZED, "404", "현재 로그인한 사용자는 해당 게시글의 수정 또는 삭제 권한이 없습니다."),

    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "501", "해당 일정을 찾을 수 없습니다."),
    BAD_REQUEST_SCHEDULE(HttpStatus.BAD_REQUEST, "503", "해당 일정은 주어진 study_id에 속한 글이 아닙니다."),
    UNAUTHORIZED_SCHEDULE(HttpStatus.UNAUTHORIZED, "504", "현재 로그인한 사용자는 해당 일정의 수정 또는 삭제 권한이 없습니다."),

    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "601", "해당 카테고리를 찾을 수 없습니다."),
    CONFLICT_CATEGORY(HttpStatus.CONFLICT, "602", "이미 해당 카테고리가 존재합니다."),

    NOT_FOUND_GOAL(HttpStatus.NOT_FOUND, "701", "해당 목표를 찾을 수 없습니다."),
    UNAUTHORIZED_GOAL(HttpStatus.UNAUTHORIZED, "704", "현재 로그인한 사용자는 해당 목표를 조회&수정&삭제할 권한이 없습니다."),

    UNAUTHORIZED_REDIRECT_URI(HttpStatus.UNAUTHORIZED, "904", "인증되지 않은 리디렉션 URI 입니다. 인증을 진행할 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
