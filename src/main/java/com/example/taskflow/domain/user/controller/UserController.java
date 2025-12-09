package com.example.taskflow.domain.user.controller;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.user.model.request.UserCreateRequest;
import com.example.taskflow.domain.user.model.request.UserInformationModyifyingRequest;
import com.example.taskflow.domain.user.model.response.UserCreateResponse;
import com.example.taskflow.domain.user.model.response.UserListInquiryResponse;
import com.example.taskflow.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping()
    public ResponseEntity<GlobalResponse<UserCreateResponse>> createUser(@Valid @RequestBody UserCreateRequest request) {

        UserCreateResponse response = userService.createUser(request);
        SuccessMessage successMessage = SuccessMessage.USER_CREATE_SUCCESS;

        return ResponseEntity.status(successMessage.getStatus())
                .body(GlobalResponse.success(successMessage, response));
    }

    //사용자 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<UserCreateResponse>> getUser(@PathVariable Long id) {
        UserCreateResponse response = userService.getUser(id);
        SuccessMessage successMessage = SuccessMessage.USER_GET_SUCCESS;
        return ResponseEntity.status(successMessage.getStatus())
                .body(GlobalResponse.success(successMessage, response));
    }

    //사용자 목록 조회
    @GetMapping()
    public ResponseEntity<Page<UserListInquiryResponse>> getUserList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<UserListInquiryResponse> result = userService.getUserList(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

//    //사용자 정보 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<GlobalResponse<UserInformationModyifyingRequest>> updateUserInfo(@PathVariable Long id, @Valid @RequestBody UserInformationModyifyingRequest request) {
//
//    }




}
