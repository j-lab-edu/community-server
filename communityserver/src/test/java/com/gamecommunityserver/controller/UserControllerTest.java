package com.gamecommunityserver.controller;

import com.gamecommunityserver.dto.UserDTO;
import com.gamecommunityserver.mapper.UserInfoMapper;
import com.gamecommunityserver.service.impl.UserServiceImpl;
import com.gamecommunityserver.utils.sha256Encrypt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.BDDMockito.given;

public class UserControllerTest {


    /**
     * TODO:@InjectMocks
     */
    @InjectMocks
    private UserServiceImpl userService;

    /**
     *TODO:@Mock
     */
    @Mock
    private UserInfoMapper userMapper;

    private final int noPermissionAdmin = 1;
    private final int notSecession = 0;
    private final int testUserNumber = 1;
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
        UserDTO userDTO = generateTestUser();
        userMapper.register(userDTO);
    }
    @Test
    @DisplayName("id 중복 체크 테스트")
    public void idOverlapCheckSuccessTest(){
        UserDTO userDTO = generateTestUser();
        given(userMapper.idCheck("testUserId")).willReturn(1);
        given(userMapper.idCheck("testUserId1")).willReturn(0);
        userService.LoginCheckPassword(userDTO.getId(), userDTO.getPassword());
    }

    @Test
    @DisplayName("유저 로그인 성공 테스트")
    public void loginUserSuccessTest(){
        UserDTO userDTO = generateTestUser();
        given(userMapper.passwordCheck("testUserId", sha256Encrypt.encrypt("testUserPassword"))).willReturn(userDTO);
        //assertThat(userService.LoginCheckPassword("testUserId","testUserPassword").equals(userDTO));
    }

    @Test
    @DisplayName("유저 정보 확인 테스트")
    public void selectUserSuccessTest(){
        UserDTO userDTO = generateTestUser();
        given(userMapper.selectUser(testUserNumber)).willReturn(userDTO);
//        assertThat(userService.selectUser(testUserNumber).equals(userDTO));
    }

    @Test
    @DisplayName("회원 탈퇴 성공 테스트")
    public void deleteUserSuccessTest(){
        UserDTO userDTO = generateTestUser();
        loginUserSuccessTest();
        userService.deleteUser(userDTO.getUserNumber());
    }

}
