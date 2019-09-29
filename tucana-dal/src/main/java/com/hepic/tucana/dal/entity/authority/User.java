package com.hepic.tucana.dal.entity.authority;

import java.util.List;

public class User extends SysUser {

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
