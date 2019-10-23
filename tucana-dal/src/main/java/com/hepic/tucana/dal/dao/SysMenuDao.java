package com.hepic.tucana.dal.dao;

import java.util.List;

import com.hepic.tucana.model.shiro.Menu;
import org.apache.ibatis.annotations.Param;

/**
 * 数据访问接口
 */
public interface SysMenuDao {

    List<Menu> selectSysMenuListByModel(Menu model);

    List<Menu> selectSysMenuByRoleId(Long roleId);

    int deleteRoleMenu(Long roleId);

    int insertRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    Menu selectSysMenuById(Long roleId);

    int insertSysMenu(Menu model);

    int updateSysMenu(Menu model);

    int deleteById(Long id);
}