package com.example.taskflow.domain.user.controller;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.user.model.request.*;
import com.example.taskflow.domain.user.model.response.*;
import com.example.taskflow.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping
    public ResponseEntity<GlobalResponse<UserCreateResponse>> createUserApi(
            @Valid @RequestBody UserCreateRequest request
    ) {
        UserCreateResponse response = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(GlobalResponse.success(SuccessMessage.USER_CREATE_SUCCESS, response));
    }

    //사용자 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<UserGetProfileResponse>> getUserApi(
            @PathVariable long id
    ) {
        UserGetProfileResponse response = userService.getUser(id);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.USER_GET_SUCCESS, response));
    }

    //사용자 목록 조회
    @GetMapping
    public ResponseEntity<GlobalResponse<List<UserListInquiryResponse>>> getUserListApi() {

        List<UserListInquiryResponse> response = userService.getUserList();

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.USER_GET_LIST_SUCCESS, response));
    }

    //사용자 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse<UserUpdateInfoResponse>> updateUserInfo(
            @PathVariable long id,
            @Valid @RequestBody UserUpdateInfoRequest request
    ) {
        UserUpdateInfoResponse response = userService.updateUserInfo(id, request);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.USER_UPDATE_SUCCESS,response));
    }

    //회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<Void>> deleteUserApi(
            @PathVariable long id
    ) {
        userService.deleteUser(id);

        return ResponseEntity.ok(GlobalResponse.successNodata(SuccessMessage.USER_DELETE_SUCCESS));
    }

    //추가 가능한 사용자 조회
    @GetMapping("/available")
    public ResponseEntity<GlobalResponse<List<UserAvailableTeamResponse>>> findTeamUser(
            @RequestParam(required = false) Long teamId
    ) {
        List<UserAvailableTeamResponse> responses = userService.findAvailableUsers(teamId);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.USER_GET_AVAILABLE_LIST_SUCCESS,responses));
    }
}
