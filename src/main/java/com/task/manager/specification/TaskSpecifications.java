package com.task.manager.specification;

import com.task.manager.entity.Task;
import com.task.manager.entity.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {

    public static Specification<Task> hasStatus(String status){
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isBlank()){
                return criteriaBuilder.conjunction();
            }
            try {
                TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
                return criteriaBuilder.equal(root.get("status"), taskStatus);
            }catch (IllegalArgumentException e){
                return criteriaBuilder.disjunction();
            }
        };

    }

    public static Specification<Task> hasTitle(String title){
        return ((root, query, cb) ->
                title == null ? null : cb.like(cb.lower(root.get("title")) , "%" + title.toLowerCase() + "%" )
                );
    }

    public static Specification<Task> hasUserId(Long userId){
        return (root, query, cb) ->  cb.equal(root.get("user").get("id"), userId);
    }
}
