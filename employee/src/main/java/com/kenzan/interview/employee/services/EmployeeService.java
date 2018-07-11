package com.kenzan.interview.employee.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kenzan.interview.employee.entity.Employee;
import com.kenzan.interview.employee.repository.EmployeeRepository;

/**
 * @author Colin, Cesar
 *
 * Employee service
 */
@Service
public class EmployeeService {
	private EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	/**
	 * Create a new employee and persists it to the database
	 * 
	 * @param employee
	 * @return Employee entity
	 */
	public Employee createEmployee(Employee employee) {
		if (!employeeRepository.existsById(employee.getId())) {
			employeeRepository.save(employee);
		}
		return null;
	}
	
	/**
	 * Updates an employee in the database
	 * 
	 * @param employee
	 */
	public void updateEmployee(Employee employee) {
		employeeRepository.save(employee);
	}
	
	/**
	 * Finds all active employees
	 * 
	 * @return a Iterable of all active employees found, empty otherwise
	 */
	public Iterable<Employee> getActivetEmployees() {
		return employeeRepository.findByActive(true);
	}
	
	/**
	 * Finds all employees
	 * 
	 * @return a Iterable of all employees found, empty otherwise
	 */
	public Iterable<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	/**
	 * Finds an employee
	 * 
	 * @param id
	 * @return the employee found, empty otherwise
	 */
	public Optional<Employee> getEmployee(String id) {
		return employeeRepository.findById(id);
	}
	
	/**
	 * Calculates the number of employees in the database
	 * 
	 * @return the total
	 */
	public long total() {
		return employeeRepository.count();
	}
}
