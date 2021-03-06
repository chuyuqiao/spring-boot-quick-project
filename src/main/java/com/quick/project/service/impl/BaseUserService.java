package com.quick.project.service.impl;

import com.quick.project.constant.RoleConstant;
import com.quick.project.entity.UserEntity;
import com.quick.project.mapper.UserMapper;
import com.quick.project.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Log4j
@AllArgsConstructor
public class BaseUserService implements UserService {

    private final UserMapper userMapper;

    @Override
    public boolean insert(UserEntity userEntity) {
        String username = userEntity.getUsername();
        if (exist(username)){
            return false;
        }
        userEntity.setRoles(RoleConstant.ROLE_USER);
        int result = userMapper.insert(userEntity);
        return  result == 1;
    }

    @Override
    public UserEntity getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 判断用户是否存在
     * @param username 账号
     * @return 密码
     */
    private boolean exist(String username){
        UserEntity userEntity = userMapper.selectByUsername(username);
        return (userEntity != null);
    }

}
