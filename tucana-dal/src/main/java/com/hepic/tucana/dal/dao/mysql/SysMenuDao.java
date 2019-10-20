package com.hepic.tucana.dal.dao.mysql;

import java.util.List;

import com.hepic.tucana.dal.entity.authority.SysMenu;
import org.apache.ibatis.annotations.Param;

/**
 * 数据访问接口
 */
public interface SysMenuDao {

    List<SysMenu> selectSysMenuListByModel(SysMenu model);

    List<SysMenu> selectSysMenuByRoleId(Long roleId);

    SysMenu selectSysMenuById(Long id);

    int deleteRoleMenu(Long id);

    int insertRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    int insertSysMenu(SysMenu model);

    int updateSysMenu(SysMenu model);

    int deleteById(Long id);
}