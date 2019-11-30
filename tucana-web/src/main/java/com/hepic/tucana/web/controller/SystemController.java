package com.hepic.tucana.web.controller;

import com.hepic.tucana.model.shiro.Menu;
import com.hepic.tucana.model.shiro.Role;
import com.hepic.tucana.model.shiro.User;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.SystemService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = "/system")
public class SystemController {

    private static final Logger log = LoggerFactory.getLogger(SystemController.class);

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
    public CommonResponse<List<Menu>> getMenuList(Menu menu) {
        CommonResponse<List<Menu>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<Menu> result = systemService.getMenuList(menu);
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return response;
    }

    /**
     * 获取根据角色菜单列表
     *
     * @param roleId 角色键
     * @return
     */
    @GetMapping(value = "getMenuIdByRoleId")
    @ResponseBody
    public CommonResponse<List<Long>> getMenuIdByRoleId(Long roleId) {
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
        return response;
    }

    /**
     * 保存菜单
     *
     * @param menu
     * @return
     */
    @PostMapping(value = "saveMenu")
    @ResponseBody
    public CommonResponse<Integer> saveMenu(@RequestBody Menu menu) {
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
        return response;
    }

    /**
     * 删除菜单
     *
     * @param menu
     * @return
     */
    @PostMapping(value = "deleteMenu")
    @ResponseBody
    public CommonResponse<Integer> deleteMenu(@RequestBody Menu menu) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = systemService.deleteMenu(menu.getId());
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("删除菜单异常", e);
        }
        return response;
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
    public CommonResponse<List<Role>> getRoleList(Role role) {
        CommonResponse<List<Role>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<Role> result = systemService.getRoleList(role);
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return response;
    }

    /**
     * 保存角色
     *
     * @param role
     * @return
     */
    @PostMapping(value = "saveRole")
    @ResponseBody
    public CommonResponse<Integer> saveRole(@RequestBody Role role) {
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
        return response;
    }

    /**
     * 删除角色
     *
     * @param role
     * @return
     */
    @PostMapping(value = "deleteRole")
    @ResponseBody
    public CommonResponse<Integer> deleteRole(@RequestBody Role role) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = systemService.deleteRole(role.getId());
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("删除角色异常", e);
        }
        return response;
    }

    /**
     * 查询用户列表
     *
     * @param user
     * @return
     */
    @GetMapping(value = "getUserList")
    @ResponseBody
    public CommonResponse<List<User>> getUserList(User user) {
        CommonResponse<List<User>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<User> result = systemService.getUserList(user);
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return response;
    }

    /**
     * 根据用户获取角色列表
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping(value = "getRoleIdByUserId")
    @ResponseBody
    public CommonResponse<List<Long>> getRoleIdByUserId(Long userId) {
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
        return response;
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @PostMapping(value = "saveUser")
    @ResponseBody
    public CommonResponse<Integer> saveUser(@RequestBody User user) {
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
        return response;
    }

    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    @PostMapping(value = "deleteUser")
    @ResponseBody
    public CommonResponse<Integer> deleteUser(@RequestBody User user) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = systemService.deleteUser(user.getId());
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("删除用户异常", e);
        }
        return response;
    }


}
