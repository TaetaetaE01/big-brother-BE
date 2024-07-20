package com.example.bigbrotherbe.member.entity.dto.request;

import com.example.bigbrotherbe.member.entity.Member;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String username;
    private String email;
    private String is_active;
    private String create_at;
    private String update_at;

    static public MemberDto toDto(Member member){
        return MemberDto.builder().id(member.getId())
            .username(member.getUsername())
            .email(member.getEmail())
            .is_active(member.getIs_active())
            .create_at(member.getCreate_at().toString())
            .update_at(member.getUpdate_at().toString()).build();
    }
}