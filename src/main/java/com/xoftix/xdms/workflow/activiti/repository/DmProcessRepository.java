package com.xoftix.xdms.workflow.activiti.repository;

import org.springframework.stereotype.Repository;

import com.xoftix.xdms.workflow.activiti.dto.DmProcess;

@Repository
public interface DmProcessRepository extends CommonRepository<DmProcess, Long> {

	DmProcess findByCode(String codigo);

}
