package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.authority.*;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/4/11.
 */
public interface LoginDao {

    @Select("SELECT id,user_name AS userName ,`password`,nick_name as nickName " +
            "FROM sys_user WHERE user_name = #{name} ; ")
    User findUserByName(String name);

    @Select("SELECT r.id,r.role_key AS roleKey,r.`name` FROM sys_user_role as ur " +
            "INNER JOIN sys_role as r ON ur.rid = r.id " +
            "WHERE ur.uid = #{userId}")
    List<Role> findRoleByUserId(Integer userId);

    @Select("SELECT p.id,p.parent,p.permission_key as permissionKey,p.`name`,p.description,p.url,p.type FROM sys_role_permission as rp " +
            "INNER JOIN sys_permission p ON rp.pid = p.id " +
            "WHERE rp.rid =  #{roleId}")
    List<SysPermission> findPermissionByRoleId(Integer roleId);
}
