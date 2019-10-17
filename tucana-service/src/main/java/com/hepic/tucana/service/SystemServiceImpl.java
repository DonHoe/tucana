package com.hepic.tucana.service;

import com.hepic.tucana.dal.dao.mysql.SysMenuDao;
import com.hepic.tucana.dal.dao.mysql.SysRoleDao;
import com.hepic.tucana.dal.dao.mysql.SysUserDao;
import com.hepic.tucana.dal.entity.authority.SysMenu;
import com.hepic.tucana.dal.entity.authority.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    SysMenuDao sysMenuDao;

    @Autowired
    SysRoleDao sysRoleDao;

    @Autowired
    SysUserDao sysUserDao;

    /**
     * 获取菜单列表
     *
     * @param sysMenu
     * @return
     */
    public List<SysMenu> getMenuList(SysMenu sysMenu) {
        List<SysMenu> result = new ArrayList<>();
        List<SysMenu> list = sysMenuDao.selectSysMenuListByModel(sysMenu);
        result = list.stream().filter(p -> p.getParentId().intValue() == 0).collect(Collectors.toList());
        for (SysMenu item : result) {
            List<SysMenu> child = list.stream().filter(p -> p.getParentId().equals(item.getId())).collect(Collectors.toList());
            child.forEach(p -> {
                List<SysMenu> subChild = list.stream().filter(q -> q.getParentId().intValue() == p.getId().intValue()).collect(Collectors.toList());
                p.setChildren(subChild);
            });
            item.setChildren(child);
        }

        return result;
    }

    /**
     * 新增菜单
     *
     * @param sysMenu
     * @return
     */
    @Override
    public int addMenu(SysMenu sysMenu) {
        return sysMenuDao.insertSysMenu(sysMenu);
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Override
    public int deleteMenu(Long id) {
        return sysMenuDao.deleteById(id);
    }

    /**
     * 编辑菜单
     *
     * @param sysMenu
     * @return
     */
    @Override
    public int editMenu(SysMenu sysMenu) {
        return sysMenuDao.updateSysMenu(sysMenu);
    }

    /**
     * 查询角色集合
     *
     * @param sysRole
     * @return
     */
    @Override
    public List<SysRole> getRoleList(SysRole sysRole) {
        return sysRoleDao.selectSysRoleListByModel(sysRole);
    }

    /**
     * 新增角色集合
     *
     * @param sysRole
     * @return
     */
    @Override
    public int addRole(SysRole sysRole) {
        return sysRoleDao.insertSysRole(sysRole);
    }

    /**
     * 编辑角色集合
     *
     * @param sysRole
     * @return
     */
    @Override
    public int editRole(SysRole sysRole) {
        return sysRoleDao.updateSysRole(sysRole);
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @Override
    public int deleteRole(Long id) {
        return sysRoleDao.deleteById(id);
    }
}
