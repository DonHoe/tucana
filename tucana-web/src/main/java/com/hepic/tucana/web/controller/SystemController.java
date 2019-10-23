package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.model.shiro.SysMenu;
import com.hepic.tucana.model.shiro.SysRole;
import com.hepic.tucana.model.shiro.SysUser;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "system")
public class SystemController {

    @Autowired
    SystemService systemService;

    /**
     * 获取菜单列表
     *
     * @param sysMenu
     * @return
     */
    @GetMapping(value = "getMenuList")
    public String getMenuList(SysMenu sysMenu) {
        CommonResponse<List<SysMenu>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<SysMenu> result = systemService.getMenuList(sysMenu);
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 获取根据角色菜单列表
     *
     * @param roleId 角色键
     * @return
     */
    @GetMapping(value = "getMenuIdByRoleId")
    public String getMenuIdByRoleId(Long roleId) {
        CommonResponse<List<Long>> response = new CommonResponse();
        try {
            List<Long> menuIds = new ArrayList<>();
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<SysMenu> result = systemService.getSysMenuByRoleId(roleId);
            if (CollectionUtils.isNotEmpty(result)) {
                menuIds = result.stream().map(p -> p.getId()).collect(Collectors.toList());
            }
            response.setResult(menuIds);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 保存菜单
     *
     * @param sysMenu
     * @return
     */
    @PostMapping(value = "saveMenu")
    public String saveMenu(@RequestBody SysMenu sysMenu) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = 0;
            if (sysMenu.getId() == null || sysMenu.getId().intValue() == 0) {
                result = systemService.addMenu(sysMenu);
            } else {
                result = systemService.editMenu(sysMenu);
            }
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("保存菜单异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 删除菜单
     *
     * @param sysMenu
     * @return
     */
    @PostMapping(value = "deleteMenu")
    public String deleteMenu(@RequestBody SysMenu sysMenu) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = systemService.deleteMenu(sysMenu.getId());
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("删除菜单异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 查询角色列表
     *
     * @param sysRole
     * @return
     */
    @GetMapping(value = "getRoleList")
    public String getRoleList(SysRole sysRole) {
        CommonResponse<List<SysRole>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<SysRole> result = systemService.getRoleList(sysRole);
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 保存角色
     *
     * @param sysRole
     * @return
     */
    @PostMapping(value = "saveRole")
    public String saveRole(@RequestBody SysRole sysRole) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = 0;
            if (sysRole.getId() == null || sysRole.getId().intValue() == 0) {
                result = systemService.addRole(sysRole);
            } else {
                result = systemService.editRole(sysRole);
            }
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("保存角色异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 删除角色
     *
     * @param sysRole
     * @return
     */
    @PostMapping(value = "deleteRole")
    public String deleteRole(@RequestBody SysRole sysRole) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = systemService.deleteRole(sysRole.getId());
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("删除角色异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 查询用户列表
     *
     * @param sysUser
     * @return
     */
    @GetMapping(value = "getUserList")
    public String getUserList(SysUser sysUser) {
        CommonResponse<List<SysUser>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<SysUser> result = systemService.getUserList(sysUser);
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 根据用户获取角色列表
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping(value = "getRoleIdByUserId")
    public String getRoleIdByUserId(Long userId) {
        CommonResponse<List<Long>> response = new CommonResponse();
        try {
            List<Long> roleIds = new ArrayList<>();
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<SysRole> result = systemService.getSysRoleByUserId(userId);
            if (CollectionUtils.isNotEmpty(result)) {
                roleIds = result.stream().map(p -> p.getId()).collect(Collectors.toList());
            }
            response.setResult(roleIds);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 保存用户
     *
     * @param sysUser
     * @return
     */
    @PostMapping(value = "saveUser")
    public String saveUser(@RequestBody SysUser sysUser) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = 0;
            if (sysUser.getId() == null || sysUser.getId().intValue() == 0) {
                result = systemService.addUser(sysUser);
            } else {
                result = systemService.editUser(sysUser);
            }
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("保存用户异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 删除用户
     *
     * @param sysUser
     * @return
     */
    @PostMapping(value = "deleteUser")
    public String deleteUser(@RequestBody SysUser sysUser) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = systemService.deleteUser(sysUser.getId());
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("删除用户异常", e);
        }
        return JSON.toJSONString(response);
    }


}
