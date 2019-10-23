package com.hepic.tucana.dal.dao;

import java.util.List;

import com.hepic.tucana.model.shiro.SysMenu;
import org.apache.ibatis.annotations.Param;

/**
 * 数据访问接口
 */
public interface SysMenuDao {

    List<SysMenu> selectSysMenuListByModel(SysMenu model);

    List<SysMenu> selectSysMenuByRoleId(Long roleId);

    int deleteRoleMenu(Long roleId);

    int insertRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    SysMenu selectSysMenuById(Long roleId);

    int insertSysMenu(SysMenu model);

    int updateSysMenu(SysMenu model);

    int deleteById(Long id);
}