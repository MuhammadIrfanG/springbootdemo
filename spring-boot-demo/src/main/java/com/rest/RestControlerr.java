package com.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rest.Employee;
@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@RequestMapping(value="/resources")
public class RestControlerr {
	@Autowired
    EmployeeService empService; 
 
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = empService.findAllEmployees();
        if (employees.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }
 
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
        Employee employee = empService.findById(id);
        if (employee == null) {
            return new ResponseEntity(new CustomError("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }
 
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
        if (empService.isEmployeeExist(employee)) {
            return new ResponseEntity(new CustomError("A Employee with name " + 
            		employee.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        empService.createEmployee(employee);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/employee/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
        Employee currentEmployee = empService.findById(id);
        if (currentEmployee == null) {
            return new ResponseEntity(new CustomError("Unable to update. Employee with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentEmployee.setName(employee.getName());
        empService.updateEmployee(currentEmployee);
        return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
    }
 
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") long id) {
        Employee employee = empService.findById(id);
        if (employee == null) {
            return new ResponseEntity(new CustomError("Unable to delete. Employee with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        empService.deleteEmployeeById(id);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }
 
}
