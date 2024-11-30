package com.zags.gorodovoy.specification;

import com.zags.gorodovoy.models.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpec {

    public static Specification<Task> userIdEquals(Long userId) {
        return (root, query, cb) -> {
            if (userId == null) return cb.isTrue(cb.literal(true));

            return cb.equal(root.get("userId"), userId);
        };
    }

    public static Specification<Task> statusIdEquals(Long statusId) {
        return (root, query, cb) -> {
            if (statusId == null) return cb.isTrue(cb.literal(true));

            return cb.equal(root.get("statusId"), statusId);
        };
    }

    public static Specification<Task> serviceIdEquals(Long serviceId) {
        return (root, query, cb) -> {
            if (serviceId == null) return cb.isTrue(cb.literal(true));

            return cb.equal(root.get("serviceId"), serviceId);
        };
    }

    public static Specification<Task> employeeIdEquals(Long employeeId) {
        return (root, query, cb) -> {
            if (employeeId == null) return cb.isTrue(cb.literal(true));

            return cb.equal(root.get("employeeId"), employeeId);
        };
    }
}