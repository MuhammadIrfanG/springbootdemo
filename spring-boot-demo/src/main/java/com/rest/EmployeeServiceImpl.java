package com.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;


@Service("empService")
public class EmployeeServiceImpl implements EmployeeService{
	private static List<Employee> employees= new ArrayList<Employee>();
	
	static{
		employees.add(new Employee(1,"Name1"));
	}

	public List<Employee> findAllEmployees() {
		return employees;
	}
	
	public Employee findById(long id) {
		for(Employee employee : employees){
			if(employee.getId() == id){
				return employee;
			}
		}
		return null;
	}
	
	public Employee findByName(String name) {
		for(Employee employee : employees){
			if(employee.getName().equalsIgnoreCase(name)){
				return employee;
			}
		}
		return null;
	}
	
	public void createEmployee(Employee employee) {
		employees.add(employee);
	}

	public void updateEmployee(Employee employee) {
		int index = employees.indexOf(employee);
		employees.set(index, employee);
	}

	public void deleteEmployeeById(long id) {
		Iterator<Employee> iterator = employees.iterator();
		while(iterator.hasNext()) {
			Employee employee = iterator.next();
			if (employee.getId() == id) {
				iterator.remove();
			}
		}
	}

	public boolean isEmployeeExist(Employee employee) {
		return findByName(employee.getName())!=null;
	}

}
