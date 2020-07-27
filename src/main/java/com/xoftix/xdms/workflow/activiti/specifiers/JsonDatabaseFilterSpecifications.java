package com.xoftix.xdms.workflow.activiti.specifiers;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public interface JsonDatabaseFilterSpecifications<Entity> {

    Predicate filter(CriteriaBuilder criteriaBuilder, Path<?> column, String jsonObject);
}
