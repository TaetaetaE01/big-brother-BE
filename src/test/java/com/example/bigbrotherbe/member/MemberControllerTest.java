package com.example.bigbrotherbe.member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.bigbrotherbe.member.entity.dto.request.MemberDto;
import com.example.bigbrotherbe.member.entity.dto.request.SignUpDto;
import com.example.bigbrotherbe.member.service.MemberService;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class MemberControllerTest {
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    @Autowired
    MemberService memberService;
    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    int randomServerPort;

    private SignUpDto signUpDto;

    @BeforeEach
    void beforeEach(){
        // Member 회원가입
        signUpDto = SignUpDto.builder()
            .username("member")
            .password("12345678")
            .email("gkstkddbs99@mju.ac.kr")
            .create_at(LocalDateTime.now().toString()).build();
    }
    @AfterEach
    void afterEach(){
        databaseCleanUp.truncateAllEntity();
    }
    @Test
    public void signUpTest(){
        String url = "http://localhost:" + randomServerPort +"/members/sign-up";
        ResponseEntity<MemberDto> responseEntity = testRestTemplate.postForEntity(url,signUpDto,
            MemberDto.class);

        // 응답 검증
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        MemberDto savedMemberDto = responseEntity.getBody();
        assertThat(savedMemberDto.getUsername()).isEqualTo(signUpDto.getUsername());
    }
//    @Test
//    public void signInTest() {
//        memberService.signUp(signUpDto);
//
//        SignInDto signInDto = SignInDto.builder()
//            .username("member")
//            .password("12345678").build();
//
//        // 로그인 요청
//        JwtToken jwtToken = memberService.signIn(signInDto);
//
//        // HttpHeaders 객체 생성 및 토큰 추가
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setBearerAuth(jwtToken.getAccessToken());
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        log.info("httpHeaders = {}", httpHeaders);
//
//        // API 요청 설정
//        String url = "http://localhost:" + randomServerPort + "/members/test";
//        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(url, new HttpEntity<>(httpHeaders), String.class);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isEqualTo(signInDto.getUsername());
//
////        assertThat(SecurityUtil.getCurrentUsername()).isEqualTo(signInDto.getUsername()); // -> 테스트 코드에서는 인증을 위한 절차를 거치지 X. SecurityContextHolder 에 인증 정보가 존재하지 않는다.
//
//
//    }
}