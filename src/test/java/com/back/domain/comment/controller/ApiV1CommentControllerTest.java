package com.back.domain.comment.controller;

import com.back.domain.comment.entity.Comment;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test") // 테스트 환경에서는 test 프로파일을 활성화합니다.
@SpringBootTest // 스프링부트 테스트 클래스임을 나타냅니다.
@AutoConfigureMockMvc // MockMvc를 자동으로 설정합니다.
@Transactional // 각 테스트 메서드가 종료되면 롤백됩니다.
public class ApiV1CommentControllerTest {
    @Autowired
    private MockMvc mvc; // MockMvc를 주입받습니다.

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("댓글 단건 조회")
    void t1() throws Exception {
        int postId = 1;
        int commentId = 1;
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/posts/" + postId + "/comments/" + commentId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()); // 응답결과를 출력합니다.

        resultActions
                // 특정 컨트롤러의 액션메서드가 실행되었는지 체크
                .andExpect(handler().handlerType(ApiV1CommentController.class))
                .andExpect(handler().methodName("getComment"))
                // 응답 코드 비교
                .andExpect(status().isOk())
                // json 값 체크 (데이터 형태 유효성만 검증)
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.createDate").isString())
                .andExpect(jsonPath("$.modifyDate").isString())
                .andExpect(jsonPath("$.content").isString())
        ;
    }

    @Test
    @DisplayName("댓글 단건 조회 - 없는 글(404)")
    void t1_404() throws Exception {
        int postId = 1;
        int commentId = Integer.MAX_VALUE;
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/posts/" + postId + "/comments/" + commentId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()); // 응답결과를 출력합니다.

        resultActions
                // 특정 컨트롤러의 액션메서드가 실행되었는지 체크
                .andExpect(handler().handlerType(ApiV1CommentController.class))
                .andExpect(handler().methodName("getComment"))
                // 응답 코드 비교
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultCode").value("404-1"))
                .andExpect(jsonPath("$.msg").value("해당 데이터가 존재하지 않습니다."))
        ;
    }

    @Test
    @DisplayName("댓글 다건 조회")
    void t2() throws Exception {
        int postId = 1;
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/posts/" + postId + "/comments")
                )
                .andDo(print());

        List<Comment> comments = postService.findCommentsById(postId);

        resultActions
                .andExpect(handler().handlerType(ApiV1CommentController.class))
                .andExpect(handler().methodName("getCommentList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(comments.size()));
        ;

        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            resultActions
                    .andExpect(jsonPath("$[%d].id".formatted(i)).value(comment.getId()))
                    .andExpect(jsonPath("$[%d].createDate".formatted(i)).value(Matchers.startsWith(comment.getCreatedDate().toString().substring(0, 20))))
                    .andExpect(jsonPath("$[%d].modifyDate".formatted(i)).value(Matchers.startsWith(comment.getModifyDate().toString().substring(0, 20))))
                    .andExpect(jsonPath("$[%d].content".formatted(i)).value(comment.getContent()));
        }
    }

    @Test
    @DisplayName("댓글 삭제")
    void t3() throws Exception {
        int postId = 1;
        int commentId = 2;
        ResultActions resultActions = mvc
                .perform(
                        delete("/api/v1/posts/" + postId + "/comments/" + commentId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()); // 응답결과를 출력합니다.

        resultActions
                // 특정 컨트롤러의 액션메서드가 실행되었는지 체크
                .andExpect(handler().handlerType(ApiV1CommentController.class))
                .andExpect(handler().methodName("delete"))
                // 응답 코드 비교
                .andExpect(status().isOk())
                // json 값 비교
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("%d번 댓글이 삭제되었습니다.".formatted(commentId)))
        ;
    }

    @Test
    @DisplayName("댓글 수정")
    void t4() throws Exception {
        int postId = 1;
        int commentId = 2;
        ResultActions resultActions = mvc
                .perform(
                        put("/api/v1/posts/" + postId + "/comments/" + commentId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "content": "내용 new"
                                        }
                                        """)
                )
                .andDo(print()); // 응답결과를 출력합니다.

        resultActions
                // 특정 컨트롤러의 액션메서드가 실행되었는지 체크
                .andExpect(handler().handlerType(ApiV1CommentController.class))
                .andExpect(handler().methodName("modify"))
                // 응답 코드 비교
                .andExpect(status().isOk())
                // json 값 비교
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("%d번 댓글이 수정되었습니다.".formatted(commentId)))
        ;
    }
}
