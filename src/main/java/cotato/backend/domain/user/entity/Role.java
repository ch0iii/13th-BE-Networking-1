package cotato.backend.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

        PRESIDENT("회장"),
        VICE_PRESIDENT("부회장"),
        PART_LEADER("파트장"),
        PLANNING_LEADER("기획팀장"),
        PROMOTION_LEADER("홍보팀장"),
        EDUCATION_LEADER("교육팀장");

        private final String value;

}
