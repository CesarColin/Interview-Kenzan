package com.kenzan.interview.employee.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kenzan.interview.employee.entity.Employee;
import com.kenzan.interview.employee.services.EmployeeService;

/**
 * @author Colin, Cesar
 *
 * Exposes the API operations and sets the logic
 */
@RestController
@RequestMapping(value="/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
    /**
     * Retrieves all active employees 
     * 
     * @return a Iterable with all active employees
     */
    @RequestMapping(method= RequestMethod.GET)
    public Iterable<Employee> getEmployees() {
        return employeeService.getActivetEmployees();
    }
	
    /**
     * Retrieves all employees (including inactive) for demo purposes
     * 
     * @return a Iterable with all employees
     */
    @RequestMapping(method= RequestMethod.GET, value="/all")
    public Iterable<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
	
    /**
     * Retrieves the info of the given employee
     * 
     * @param id as path
     * @return Employee object with employee's info
     */
    @RequestMapping(method= RequestMethod.GET, value="/{id}")
    public Employee getEmployee(@PathVariable(value="id") String id) {
        Employee employee = verifyEmployee(id);
        return employee;
    }
    
    /**
     * Adds an employee into the database
     * 
     * @param employee
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmployee(@RequestBody @Validated Employee employee) {
    	verifyNewEmployee(employee.getId());
		employee.setActive(true);
    	employeeService.createEmployee(employee);
    }
    
    /**
     * Updates an employee using PUT method, which needs the full json to be on the body
     * 
     * @param id as path
     * @param employee
     */
    @RequestMapping(method = RequestMethod.PUT, value="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmployee(@PathVariable(value="id") String id, @RequestBody @Validated Employee employee) {
    	verifyEmployee(id);
    	employee.setId(id);
    	employee.setActive(true);
    	employeeService.updateEmployee(employee);
    }
    
    /**
     * Updates an employee using PATH method, which allows a partial jason to be in the body
     * @param id as path
     * @param employee
     */
    @RequestMapping(method = RequestMethod.PATCH, value="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyEmployee(@PathVariable(value="id") String id, @RequestBody @Validated Employee employee) {
    	Employee current = verifyEmployee(id);
    	if (employee.getFirstName() != null) {
    		current.setFirstName(employee.getFirstName());
    	}
    	if (employee.getMiddleInitial() != null) {
    		current.setMiddleInitial(employee.getMiddleInitial());
    	}
    	if (employee.getLastName() != null) {
    		current.setLastName(employee.getLastName());
    	}
    	if (employee.getDateOfBirth() != null) {
    		current.setDateOfBirth(employee.getDateOfBirth());;
    	}
    	if (employee.getDateOfEmployment() != null) {
    		current.setDateOfEmployment(employee.getDateOfEmployment());;
    	}
    	employeeService.updateEmployee(current);
    }
	
    /**
     * Deactivates an employee with out actually deleting it from the database
     * 
     * @param id as path
     */
    @RequestMapping(method= RequestMethod.DELETE, value="/{id}")
    public void deleteEmployee(@PathVariable(value="id") String id) {
        Employee employee = verifyEmployee(id);
        if (employee.isActive()) {
    		employee.setActive(false);
    		employeeService.updateEmployee(employee);
        }
    }
    
    /**
     * Verifies if the given employee exists and if it's active
     * 
     * @param id
     * @return Employee object in case it exists and is active
     * @throws NoSuchElementException
     */
    private Employee verifyEmployee(String id) throws NoSuchElementException {
        Optional<Employee> employee = employeeService.getEmployee(id);
        if (!employee.isPresent()) {
            throw new NoSuchElementException("Employee " + id + " does not exist ");
        }
        if (!employee.get().isActive()) {
        	throw new NoSuchElementException("Employee " + id + " is inactive");
        }
        return employee.get();
    }
    
    /**
     * Verifies the employee doesn't exists before a creation
     * 
     * @param id
     * @throws IllegalArgumentException
     */
    private void verifyNewEmployee(String id) throws IllegalArgumentException {
        Optional<Employee> employee = employeeService.getEmployee(id);
        if (employee.isPresent()) {
            throw new IllegalArgumentException("Employee " + id + " already exist ");
        }
    }
    
    /**
     * Exception handler if NoSuchElementException (EMPLOYEE NOT FOUND) is thrown in this Controller
     * 
     * @param ex
     * @return Error message string
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }
    
    /**
     * Exception handler if IllegalArgumentException (EMPLOYEE ALREADY EXISTS) is thrown in this Controller
     * 
     * @param ex
     * @return Error message string
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalArgumentException.class)
    public String return409(IllegalArgumentException ex) {
        return ex.getMessage();

    }

}
