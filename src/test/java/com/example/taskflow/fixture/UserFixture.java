package com.example.taskflow.fixture;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.common.utils.PasswordEncoder;

public class UserFixture {

    static PasswordEncoder passwordEncoder = new PasswordEncoder();

    public static String DEFAULT_USERNAME = "test";
    public static String DEFAULT_EMAIL = "test@test.com";
    public static String DEFAULT_PASSWORD = "qwer1234!";
    public static String ENCODED_PASSWORD = passwordEncoder.encode(DEFAULT_PASSWORD);
    public static String DEFAULT_NAME = "test";
    public static UserRole DEFAULT_ROLE = UserRole.USER;

    public static User createTestUser() {
        return new User(DEFAULT_USERNAME, DEFAULT_EMAIL, ENCODED_PASSWORD, DEFAULT_NAME, DEFAULT_ROLE);
    }
}