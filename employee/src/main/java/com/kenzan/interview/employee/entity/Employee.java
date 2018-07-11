package com.kenzan.interview.employee.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Colin, Cesar
 *
 *The Employee contains all attributes of an employee
 */
@Entity
public class Employee {
	@Id
	@Column
	private String id;
	
	@Column(nullable=false)
	private String firstName;
	
	@Column(length=1)
	private String middleInitial;
	
	@Column(nullable=false)
	private String lastName;
	
	@Column
	private Date dateOfBirth;
	
	@Column
	private Date dateOfEmployment;
	
	@Column
	private boolean active;

	public Employee(String id, String firstName, String middleInitial, String lastName, Date dateOfBirth, Date dateOfEmployment) {
		this.id = id;
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.dateOfEmployment = dateOfEmployment;
		this.active = true;
	}
	
	protected Employee() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfEmployment() {
		return dateOfEmployment;
	}

	public void setDateOfEmployment(Date dateOfEmployment) {
		this.dateOfEmployment = dateOfEmployment;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
