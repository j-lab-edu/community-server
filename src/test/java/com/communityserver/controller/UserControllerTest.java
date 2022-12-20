package com.communityserver.controller;

import com.communityserver.dto.UserDTO;
import com.communityserver.exception.DuplicateIdException;
import com.communityserver.mapper.UserInfoMapper;
import com.communityserver.service.impl.UserServiceImpl;
import com.communityserver.utils.sha256Encrypt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class UserControllerTest {


    /**
     * TODO:@InjectMocks
     */
    @InjectMocks
    private UserServiceImpl userService;
    private final UserInfoMapper userMapper = mock(UserInfoMapper.class);

    private final int noPermissionAdmin = 1;
    private final int notSecession = 0;
    private final int testUserNumber = 1;
    private final int testFailUserNumber =2;
    public UserDTO generateTestUser(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        UserDTO userDTO = new UserDTO();
        userDTO.setUserNumber(testUserNumber);
        userDTO.setId("textUserId");
        userDTO.setPassword(sha256Encrypt.encrypt("testUserPassword"));
        userDTO.setName("testUserName");
        userDTO.setAdmin(noPermissionAdmin);
        userDTO.setCreateTime(new Date());
        userDTO.setUserSecession(notSecession);
        return userDTO;
    }

    @Test
    @DisplayName("유저 회원가입 성공 테스트")
    public void signUpSuccessTest(){
        final UserDTO userDTO = generateTestUser();
        userService.register(userDTO);
    }

    @Test
    @DisplayName("유저 회원가입 실패 테스트 (1. 아이디 중복)")
    public void signUpFailTest(){
        // 1. 아이디 중복 (예외가 커스텀 예외중 중복 아이디 예외가 발생하는지)
        final UserDTO userDTO = generateTestUser();
        final UserDTO emptyUserDTO = UserDTO.builder().build();
        userService.register(userDTO);
        assertEquals(userService.register(userDTO).getId(), emptyUserDTO.getId());
    }

    @Test
    @DisplayName("유저 로그인 성공 테스트")
    public void loginUserSuccessTest(){
        UserDTO userDTO = generateTestUser();
        given(userMapper.passwordCheck("testUserId", sha256Encrypt.encrypt("testUserPassword"))).willReturn(userDTO);
        assertThat(userService.LoginCheckPassword("testUserId","testUserPassword").equals(userDTO));
    }

    @Test
    @DisplayName("유저 로그인 실패 테스트")
    public void loginUserFailTest(){
        UserDTO userDTO = generateTestUser();
        UserDTO notExistUser = UserDTO.builder().build();
        //given(userMapper.passwordCheck("testUserId", sha256Encrypt.encrypt("testFailUserPassword"))).willReturn(null);
        assertEquals(userService.LoginCheckPassword("testUserId","testFailUserPassword").getUserNumber()
                , notExistUser.getUserNumber());
    }

    @Test
    @DisplayName("유저 정보 확인 성공 테스트")
    public void selectUserSuccessTest(){
        UserDTO userDTO = generateTestUser();
        given(userMapper.selectUser(testUserNumber)).willReturn(userDTO);
        assertEquals(userMapper.selectUser(testUserNumber), userDTO);
    }

    @Test
    @DisplayName("유저 정보 확인 실패 테스트")
    public void selectUserFailTest(){
        UserDTO userDTO = generateTestUser();
        given(userMapper.selectUser(testUserNumber)).willReturn(userDTO);
//        assertThat(userService.selectUser(testFailUserNumber).equals(userDTO));
    }
    @Test
    @DisplayName("회원 탈퇴 성공 테스트")
    public void deleteUserSuccessTest(){
        UserDTO userDTO = generateTestUser();
        userService.deleteUser(userDTO.getUserNumber());
    }

}
