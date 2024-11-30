package com.zags.gorodovoy.dtoModels;

import com.zags.gorodovoy.models.Employee;
import lombok.Data;

@Data
public class employeePrivilegeReq {
    private String privilegeRole;
    private Employee employee;


    }

