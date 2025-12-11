package com.example.taskflow.domain.user.controller;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.user.model.request.UserCreateRequest;
import com.example.taskflow.domain.user.model.request.UserUpdateInfoRequest;
import com.example.taskflow.domain.user.model.response.UserCreateResponse;
import com.example.taskflow.domain.user.model.response.UserGetProfileResponse;
import com.example.taskflow.domain.user.model.response.UserListInquiryResponse;
import com.example.taskflow.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.taskflow.common.model.enums.SuccessMessage.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping
    public ResponseEntity<GlobalResponse<UserCreateResponse>> createUserApi(@Valid @RequestBody UserCreateRequest request) {

        UserCreateResponse response = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(GlobalResponse.success(USER_CREATE_SUCCESS, response));
    }

    //사용자 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<UserGetProfileResponse>> getUserApi(@PathVariable Long id) {

        UserGetProfileResponse response = userService.getUser(id);

        return ResponseEntity.ok(GlobalResponse.success(USER_GET_SUCCESS, response));
    }

    //사용자 목록 조회
    @GetMapping
    public ResponseEntity<GlobalResponse<PageResponse<UserListInquiryResponse>>> getUserListApi(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<UserListInquiryResponse> response = userService.getUserList(pageable);

        return ResponseEntity.ok(GlobalResponse.success(USER_GET_LIST_SUCCESS, response));
    }

    //사용자 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse<UserUpdateInfoRequest>> updateUserInfo(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateInfoRequest request
    ) {
        UserUpdateInfoRequest response = userService.updateUserInfo(id, request);

        return ResponseEntity.ok(GlobalResponse.success(USER_UPDATE_SUCCESS,response));
    }

    //회원 탈퇴
    @DeleteMapping("/{id}")
    public void deleteUserApi(@PathVariable Long id) {
        userService.deleteUser(id);



    }
}
