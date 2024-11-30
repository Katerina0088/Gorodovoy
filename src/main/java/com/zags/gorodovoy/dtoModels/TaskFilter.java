package com.zags.gorodovoy.dtoModels;

import lombok.Data;

@Data
public class TaskFilter {
    private Long serviceId;
    private Long userId;
    private Long statusId;
    private Long taskId;
    private Long employeeId;
}
