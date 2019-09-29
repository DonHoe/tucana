package com.hepic.tucana.dal.entity.authority;

import java.util.List;

public class Role extends SysRole {

    private List<SysPermission> permissions;

    public List<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermission> permissions) {
        this.permissions = permissions;
    }
}
