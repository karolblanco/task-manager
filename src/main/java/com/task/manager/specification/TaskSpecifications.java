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

              //  status == null ? null : criteriaBuilder.equal(criteriaBuilder.lower(root.get("status")) , status.toLowerCase());

    }

    public static Specification<Task> hasTitle(String title){
        return ((root, query, cb) ->
                title == null ? null : cb.like(cb.lower(root.get("title")) , "%" + title.toLowerCase() + "%" )
                );
    }
}
