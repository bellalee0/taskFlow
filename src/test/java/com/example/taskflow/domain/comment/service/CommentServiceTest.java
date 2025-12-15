package com.example.taskflow.domain.comment.service;

import static com.example.taskflow.common.exception.ErrorMessage.COMMENT_NOT_FOUND_TASK_OR_COMMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.taskflow.common.entity.Comment;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.comment.model.request.CommentCreateRequest;
import com.example.taskflow.domain.comment.model.request.CommentUpdateRequest;
import com.example.taskflow.domain.comment.model.response.CommentCreateResponse;
import com.example.taskflow.domain.comment.model.response.CommentGetResponse;
import com.example.taskflow.domain.comment.model.response.CommentUpdateResponse;
import com.example.taskflow.domain.comment.repository.CommentRepository;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.CommentFixture;
import com.example.taskflow.fixture.TaskFixture;
import com.example.taskflow.fixture.UserFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("댓글 생성 테스트 - 성공: 유효한 내용 입력")
    void createComment_success_parentComment() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        CommentCreateRequest request = new CommentCreateRequest();
        ReflectionTestUtils.setField(request, "content", "테스트 댓글 생성");
        ReflectionTestUtils.setField(request, "parentId", null);

        when(userRepository.findUserByUsername(anyString())).thenReturn(user);
        when(taskRepository.findTaskById(anyLong())).thenReturn(task);

        // When
        CommentCreateResponse response = commentService.createComment(user.getUsername(), 1L, request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isEqualTo("테스트 댓글 생성");

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("대댓글 생성 테스트 - 성공: 유효한 내용 입력")
    void createComment_success_childComment() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Comment parentComment = CommentFixture.createTestParentComment(user, task);
        ReflectionTestUtils.setField(parentComment, "id", 1L);

        CommentCreateRequest request = new CommentCreateRequest();
        ReflectionTestUtils.setField(request, "content", "테스트 대댓글 생성");
        ReflectionTestUtils.setField(request, "parentId", 1L);

        when(userRepository.findUserByUsername(anyString())).thenReturn(user);
        when(taskRepository.findTaskById(anyLong())).thenReturn(task);
        when(commentRepository.findParentCommentById(anyLong())).thenReturn(parentComment);

        // When
        CommentCreateResponse response = commentService.createComment(user.getUsername(), 1L, request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isEqualTo("테스트 대댓글 생성");

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("대댓글 생성 테스트 - 실패: 대댓글에 댓글 달기 시도")
    void createComment_failure_noParentComment() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Comment parentComment = CommentFixture.createTestParentComment(user, task);
        ReflectionTestUtils.setField(parentComment, "id", 1L);

        Comment childComment = CommentFixture.createTestChildComment(user, task, parentComment);
        ReflectionTestUtils.setField(parentComment, "id", 2L);

        CommentCreateRequest request = new CommentCreateRequest();
        ReflectionTestUtils.setField(request, "content", "테스트 대댓글 생성");
        ReflectionTestUtils.setField(request, "parentId", 2L);

        when(userRepository.findUserByUsername(anyString())).thenReturn(user);
        when(taskRepository.findTaskById(anyLong())).thenReturn(task);
        when(commentRepository.findParentCommentById(anyLong())).thenReturn(childComment);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> commentService.createComment(user.getUsername(), 1L, request));
        assertEquals("작업을 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("댓글 목록 조회 테스트 - 성공")
    void getCommentList_success() {

        // Given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Comment parentComment = CommentFixture.createTestParentComment(user, task);
        ReflectionTestUtils.setField(parentComment, "id", 1L);

        Page<Comment> commentPage = new PageImpl<>(List.of(parentComment), pageable, 1);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);
        when(commentRepository.findByTaskId(1L, pageable)).thenReturn(commentPage);

        // When
        PageResponse<CommentGetResponse> response = commentService.getCommentList(1L, pageable);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).getContent()).isEqualTo(parentComment.getContent());
    }

    @Test
    @DisplayName("댓글 목록 조회 테스트 - 성공: 댓글이 없을 때")
    void getCommentList_success_noComments() {

        // Given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Page<Comment> commentPage = new PageImpl<>(List.of(), pageable, 0);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);
        when(commentRepository.findByTaskId(1L, pageable)).thenReturn(commentPage);

        // When
        PageResponse<CommentGetResponse> response = commentService.getCommentList(1L, pageable);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(0);
    }

    @Test
    @DisplayName("댓글 수정 테스트 - 성공: 유효한 내용 입력")
    void updateComment_success() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Comment comment = CommentFixture.createTestParentComment(user, task);
        ReflectionTestUtils.setField(comment, "id", 1L);

        CommentUpdateRequest request = new CommentUpdateRequest();
        ReflectionTestUtils.setField(request, "content", "댓글 내용 수정");

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);
        when(commentRepository.findCommentById(anyLong())).thenReturn(comment);

        // When
        CommentUpdateResponse response = commentService.updateComment(1L, 1L, user.getUsername(), request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isEqualTo("댓글 내용 수정");
    }

    @Test
    @DisplayName("댓글 수정 테스트 - 실패: 다른 유저의 댓글 수정 시도")
    void updateComment_failure() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Comment comment = CommentFixture.createTestParentComment(user, task);
        ReflectionTestUtils.setField(comment, "id", 1L);

        CommentUpdateRequest request = new CommentUpdateRequest();
        ReflectionTestUtils.setField(request, "content", "댓글 내용 수정");

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);
        when(commentRepository.findCommentById(anyLong())).thenReturn(comment);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> commentService.updateComment(1L, 1L, "작성자 아님", request));
        assertEquals("댓글을 수정할 권한이 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("댓글 삭제 테스트 - 성공: 댓글만 있을 때")
    void deleteComment_success_onlyParentComment() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Comment comment = CommentFixture.createTestParentComment(user, task);
        ReflectionTestUtils.setField(comment, "id", 1L);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);
        when(commentRepository.findCommentById(anyLong())).thenReturn(comment);
        when(commentRepository.existsByParentCommentId(anyLong())).thenReturn(false);

        // When & Then
        commentService.deleteComment(1L, 1L, user.getUsername());
    }

    @Test
    @DisplayName("댓글 삭제 테스트 - 성공: 댓글과 대댓글 모두 삭제")
    void deleteComment_success_withChildComment() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Comment parentComment = CommentFixture.createTestParentComment(user, task);
        ReflectionTestUtils.setField(parentComment, "id", 1L);

        Comment childComment = CommentFixture.createTestChildComment(user, task, parentComment);
        ReflectionTestUtils.setField(childComment, "id", 2L);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);
        when(commentRepository.findCommentById(anyLong())).thenReturn(parentComment);
        when(commentRepository.existsByParentCommentId(anyLong())).thenReturn(true);
        when(commentRepository.findAllByParentCommentId(anyLong())).thenReturn(List.of(childComment));

        // When & Then
        commentService.deleteComment(1L, 1L, user.getUsername());
    }

    @Test
    @DisplayName("댓글 삭제 테스트 - 실패: 이미 삭제된 댓글")
    void deleteComment_failure_deletedComment() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Comment comment = CommentFixture.createTestParentComment(user, task);
        ReflectionTestUtils.setField(comment, "id", 1L);
        ReflectionTestUtils.setField(comment, "isDeleted", true);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);
        when(commentRepository.findCommentById(anyLong())).thenReturn(comment);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> commentService.deleteComment(1L, 1L, user.getUsername()));
        assertEquals("댓글을 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("댓글 삭제 테스트 - 실패: 다른 유저의 댓글 삭제 시도")
    void deleteComment_failure_unauthorized() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Comment comment = CommentFixture.createTestParentComment(user, task);
        ReflectionTestUtils.setField(comment, "id", 1L);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);
        when(commentRepository.findCommentById(anyLong())).thenReturn(comment);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> commentService.deleteComment(1L, 1L, "작성자 아님"));
        assertEquals("댓글을 삭제할 권한이 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("작업과 댓글의 관계 확인 - 실패: 댓글이 해당 작업의 댓글이 아닐 때")
    void checkTaskCommentRelationship_failure() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task1 = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task1, "id", 1L);

        Task task2 = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task2, "id", 2L);

        Comment comment = CommentFixture.createTestParentComment(user, task1);
        ReflectionTestUtils.setField(comment, "id", 1L);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task2);
        when(commentRepository.findCommentById(anyLong())).thenReturn(comment);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> commentService.deleteComment(2L, 1L, user.getUsername()));
        assertEquals("작업을 찾을 수 없습니다.", exception.getMessage());
    }
}