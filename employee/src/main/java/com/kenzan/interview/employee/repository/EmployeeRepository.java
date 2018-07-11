package com.kenzan.interview.employee.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.kenzan.interview.employee.entity.Employee;

/**
 * @author Colin, Cesar
 *
 * Employee repository interface
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {
	/**
	 * Retrieves all active employees
	 * 
	 * @param Active property
	 * @return a List of employees
	 */
	List<Employee> findByActive(boolean Active);
	/**
	 * Retrieves all active employees for the given IDs
	 * 
	 * @param Active property
	 * @param ids to find
	 * @return a List of employees in case at least one was found and is active, empty otherwise 
	 */
	List<Employee> findByActiveAndIdIn(boolean Active, Iterable<String> ids);

	//Not exposed by Spring Data REST
	@Override
	@RestResource(exported=false)
	default void deleteById(String id) {
	}

	//Not exposed by Spring Data REST
	@Override()
	@RestResource(exported=false)
	default void delete(Employee employee) {
	}

	//Not exposed by Spring Data REST
	@Override
	@RestResource(exported=false)
	default void deleteAll(Iterable<? extends Employee> entities) {
	}

	//Not exposed by Spring Data REST
	@Override
	@RestResource(exported=false)
	default void deleteAll() {
	}
}
