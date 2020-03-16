package com.zk.device.dal;

import com.zk.device.model.shiro.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 */
@Mapper
public interface SysUserDao {

    List<User> selectSysUserListByModel(User model);

    User selectSysUserById(Long id);

    List<String> findRoleKeyByUserId(Long userId);

    List<String> findPermitByUserId(Long userId);

    User selectSysUserByName(String userName);

    int insertSysUser(User model);

    int updateSysUser(User model);

    int deleteById(Long id);

}
