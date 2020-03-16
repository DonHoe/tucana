package com.zk.device.service;

import com.zk.device.model.shiro.User;
import java.util.List;

/**
 *用户服务类
 */
public interface SysUserService {

    List<User> selectSysUserListByModel(User model);

    User selectSysUserById(Long id);

    int insertSysUser(User model);

    int updateSysUser(User model);
}
