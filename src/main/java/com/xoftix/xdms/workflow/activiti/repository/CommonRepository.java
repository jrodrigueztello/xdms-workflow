package com.xoftix.xdms.workflow.activiti.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonRepository<T, K> extends JpaRepository<T, K>, JpaSpecificationExecutor<T> {

	List<T> findByEstado(Character estado);

	Page<T> findByEstado(Pageable pag, Character estado);

	T findOneByIdAndEstado(K id, Character estado);

	Long countByEstado(Character estado);
}
