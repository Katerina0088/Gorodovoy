package com.zags.gorodovoy.dtoModels;

import com.zags.gorodovoy.models.Employee;

public class employeePrivilegeReq {
    private String privilegeRole;
    private Employee employee;

    public String getPrivilegeRole() {
        return privilegeRole;
    }

    public Employee getEmployee() {
        return employee;
    }

}
