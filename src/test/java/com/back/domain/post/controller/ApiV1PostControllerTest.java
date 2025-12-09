package com.back.domain.post.controller;

import com.back.domain.post.entity.Post;
import com.back.domain.post.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test") // 테스트 환경에서는 test 프로파일을 활성화합니다.
@SpringBootTest // 스프링부트 테스트 클래스임을 나타냅니다.
@AutoConfigureMockMvc // MockMvc를 자동으로 설정합니다.
@Transactional // 각 테스트 메서드가 종료되면 롤백됩니다.
public class ApiV1PostControllerTest {
    @Autowired
    private MockMvc mvc; // MockMvc를 주입받습니다.

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("글 쓰기")
    void t1() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "제목",
                                            "content": "내용"
                                        }
                                        """)
                )
                .andDo(print()); // 응답결과를 출력합니다.

        Post post = postService.findLatest().get();

        // 201 Created 상태코드 검증
        resultActions
                // 특정 컨트롤러의 액션메서드가 실행되었는지 체크
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("writePost"))
                // 응답 코드 비교
                .andExpect(status().isCreated())
                // json 값 비교
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("%d번 글이 생성되었습니다.".formatted(post.getId())))
                .andExpect(jsonPath("$.data.id").value(post.getId()))
                .andExpect(jsonPath("$.data.createDate").value(Matchers.startsWith(post.getCreatedDate().toString().substring(0, 20))))
                .andExpect(jsonPath("$.data.modifyDate").value(Matchers.startsWith(post.getModifyDate().toString().substring(0, 20))))
                .andExpect(jsonPath("$.data.title").value("제목"))
                .andExpect(jsonPath("$.data.content").value("내용"))
        ;
    }

    @Test
    @DisplayName("글 수정")
    void t2() throws Exception {
        int id = 1;
        ResultActions resultActions = mvc
                .perform(
                        put("/api/v1/posts/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "제목 new",
                                            "content": "내용 new"
                                        }
                                        """)
                )
                .andDo(print()); // 응답결과를 출력합니다.

        resultActions
                // 특정 컨트롤러의 액션메서드가 실행되었는지 체크
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("modify"))
                // 응답 코드 비교
                .andExpect(status().isOk())
                // json 값 비교
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("%d번 글이 수정되었습니다.".formatted(id)))
        ;

        // 수정된 내용 체크 (과한 테스트, 선택사항)
//        Post post = postService.findById(id).get();
//        assertThat(post.getTitle()).isEqualTo("제목 new");
//        assertThat(post.getContent()).isEqualTo("내용 new");
    }

    @Test
    @DisplayName("글 삭제")
    void t3() throws Exception {
        int id = 1;
        ResultActions resultActions = mvc
                .perform(
                        delete("/api/v1/posts/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()); // 응답결과를 출력합니다.

        resultActions
                // 특정 컨트롤러의 액션메서드가 실행되었는지 체크
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("delete"))
                // 응답 코드 비교
                .andExpect(status().isOk())
                // json 값 비교
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("%d번 글이 삭제되었습니다.".formatted(id)))
        ;
    }
}
