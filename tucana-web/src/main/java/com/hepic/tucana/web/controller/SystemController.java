package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.model.shiro.Menu;
import com.hepic.tucana.model.shiro.Role;
import com.hepic.tucana.model.shiro.User;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping(value = "/system")
public class SystemController {

    @Autowired
    SystemService systemService;

    @GetMapping("/menu")
    public String code() {
        return "system/menu";
    }

    @GetMapping("/role")
    public String role() {
        return "system/role";
    }

    @GetMapping("/user")
    public String user() {
        return "system/user";
    }

    /**
     * 获取菜单列表
     *
     * @param menu
     * @return
     */
    @GetMapping(value = "getMenuList")
    @ResponseBody
    public String getMenuList(Menu menu) {
        CommonResponse<List<Menu>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<Menu> result = systemService.getMenuList(menu);
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
    @ResponseBody
    public String getMenuIdByRoleId(Long roleId) {
        CommonResponse<List<Long>> response = new CommonResponse();
        try {
            List<Long> menuIds = new ArrayList<>();
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<Menu> result = systemService.getSysMenuByRoleId(roleId);
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
     * @param menu
     * @return
     */
    @PostMapping(value = "saveMenu")
    @ResponseBody
    public String saveMenu(@RequestBody Menu menu) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = 0;
            if (menu.getId() == null || menu.getId().intValue() == 0) {
                result = systemService.addMenu(menu);
            } else {
                result = systemService.editMenu(menu);
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
     * @param menu
     * @return
     */
    @PostMapping(value = "deleteMenu")
    @ResponseBody
    public String deleteMenu(@RequestBody Menu menu) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = systemService.deleteMenu(menu.getId());
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
     * @param role
     * @return
     */
    @RequiresPermissions("dispatch:shipBerth:view")
    @GetMapping(value = "getRoleList")
    @ResponseBody
    public String getRoleList(Role role) {
        CommonResponse<List<Role>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<Role> result = systemService.getRoleList(role);
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
     * @param role
     * @return
     */
    @PostMapping(value = "saveRole")
    @ResponseBody
    public String saveRole(@RequestBody Role role) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = 0;
            if (role.getId() == null || role.getId().intValue() == 0) {
                result = systemService.addRole(role);
            } else {
                result = systemService.editRole(role);
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
     * @param role
     * @return
     */
    @PostMapping(value = "deleteRole")
    @ResponseBody
    public String deleteRole(@RequestBody Role role) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = systemService.deleteRole(role.getId());
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
     * @param user
     * @return
     */
    @GetMapping(value = "getUserList")
    @ResponseBody
    public String getUserList(User user) {
        CommonResponse<List<User>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<User> result = systemService.getUserList(user);
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
    @ResponseBody
    public String getRoleIdByUserId(Long userId) {
        CommonResponse<List<Long>> response = new CommonResponse();
        try {
            List<Long> roleIds = new ArrayList<>();
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<Role> result = systemService.getSysRoleByUserId(userId);
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
     * @param user
     * @return
     */
    @PostMapping(value = "saveUser")
    @ResponseBody
    public String saveUser(@RequestBody User user) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = 0;
            if (user.getId() == null || user.getId().intValue() == 0) {
                result = systemService.addUser(user);
            } else {
                result = systemService.editUser(user);
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
     * @param user
     * @return
     */
    @PostMapping(value = "deleteUser")
    @ResponseBody
    public String deleteUser(@RequestBody User user) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = systemService.deleteUser(user.getId());
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("删除用户异常", e);
        }
        return JSON.toJSONString(response);
    }


}
