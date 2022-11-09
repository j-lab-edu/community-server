package com.gamecommunityserver.service.impl;

import com.gamecommunityserver.dto.UserDTO;
import com.gamecommunityserver.exception.DuplicateIdException;
import com.gamecommunityserver.mapper.UserInfoMapper;
import com.gamecommunityserver.service.UserService;
import com.gamecommunityserver.utils.sha256Encrypt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserInfoMapper userMapper;

    public UserServiceImpl(UserInfoMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO register(UserDTO userDTO){
        if(idOverlapCheck(userDTO.getId()))
            throw new DuplicateIdException("중복된 ID 입니다.");

        userDTO.setPassword(sha256Encrypt.encrypt(userDTO.getPassword()));
        userDTO.setAdmin(0);
        userDTO.setUserSecession(0);
        userMapper.register(userDTO);

        return userDTO;
    }
    @Override
    public boolean idOverlapCheck(String id){
        return userMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO LoginCheckPassword(String id, String password){
        password = sha256Encrypt.encrypt(password);
        return userMapper.passwordCheck(id, password);
    }
    @Override
    public UserDTO selectUser(int userNumber){
        return userMapper.selectUser(userNumber);
    }

    @Override
    public void deleteUser(int userNumber){
        userMapper.deleteUser(userNumber);
    }

    @Override
    public int adminUserCheck(int userNumber){
        return userMapper.adminUserCheck(userNumber);
    }

}
