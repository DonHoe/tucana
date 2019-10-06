package com.hepic.tucana.service;

import com.hepic.tucana.dal.entity.authority.SysMenu;

import java.util.List;

public interface SystemService {

    /**
     * 获取菜单列表
     *
     * @param sysMenu
     * @return
     */
    List<SysMenu> getMenuList(SysMenu sysMenu);

    /**
     * 新增菜单
     *
     * @param sysMenu
     * @return
     */
    int addMenu(SysMenu sysMenu);

    /**
     * 编辑菜单
     *
     * @param sysMenu
     * @return
     */
    int editMenu(SysMenu sysMenu);

}
