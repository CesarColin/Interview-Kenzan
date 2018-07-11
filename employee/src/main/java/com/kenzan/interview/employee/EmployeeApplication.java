package com.kenzan.interview.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzan.interview.employee.entity.Employee;
import com.kenzan.interview.employee.services.EmployeeService;

import static com.kenzan.interview.employee.EmployeeApplication.EmployeesFromFile.importEmployees;

/**
 * @author Colin, Cesar
 *
 * Main Class for the Spring Boot Application
 */
@SpringBootApplication
public class EmployeeApplication implements CommandLineRunner{

	@Autowired
	private EmployeeService employeeService;
	
	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	/** 
	 * Method invoked after this class has been instantiated by Spring container
	 * Initializes the in-memory H2 database with all the employees.
	 *
	 * @param strings
	 * @throws Exception if problem occurs.
	 */
	@Override
	public void run(String... args) throws Exception {
		//Persist the Employees to the database
		String fileName;
		if (args.length > 0) {
			fileName = args[0];
			System.out.println(" ");
			System.out.println("FileName: " + args[0]);
			System.out.println(" ");
		}
		else {
			fileName = null;
		}
		importEmployees(fileName).forEach(e->employeeService.createEmployee(new Employee(
				e.id, 
				e.firstName, 
				e.middleInitial, 
				e.lastName, 
				stringToDate(e.dateOfBirth), 
				stringToDate(e.dateOfEmployment))));
		System.out.println("Amount of employees imported: " + employeeService.total());
	}
	
	private Date stringToDate(String sDate) {
		java.util.Date date;
		if (sDate != null) {
			try {
				date =  new SimpleDateFormat("MM/dd/yyyy").parse(sDate);
			} catch (ParseException e) {
				date = new java.util.Date();
			}
		}
		else {
			date = new java.util.Date();
		}
		return new Date(date.getTime());
	}
	
	/**
	 * Helper class to import the employees in the json
	 */
	static class EmployeesFromFile {
		private String id, firstName, middleInitial, lastName, dateOfBirth, dateOfEmployment;
		
		/**
		 * Open the json, unmarshal every entry into a EmployeesFromFile Object.
		 * 
		 * @return a List of EmployeesFromFile objects.
		 * @throws IOException
		 */
		static List<EmployeesFromFile> importEmployees(String fileName) throws IOException {
			if (fileName != null) {
				// Uses json file provided as parameter
				File initialFile = new File(fileName);
			    InputStream targetStream = new FileInputStream(initialFile);
			    return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).
			    		readValue(targetStream, new TypeReference<List<EmployeesFromFile>>() {});
			}
			else {
				// Uses EmployeesList.json provided as resource
				return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).
						readValue(EmployeesFromFile.class.getResourceAsStream("/EmployeesList.json"),
								new TypeReference<List<EmployeesFromFile>>() {});
			}
		}
	}
}
