package com.rest;


import java.util.List;

public interface EmployeeService {
	
	Employee findById(long id);
	Employee findByName(String name);
	void createEmployee(Employee employee);
	void updateEmployee(Employee employee);
	void deleteEmployeeById(long id);
	List<Employee> findAllEmployees();
	boolean isEmployeeExist(Employee employee);
	
}
