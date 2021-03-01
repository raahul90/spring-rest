package com.emp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emp.dao.Employee;
import com.emp.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public String show() {
		System.out.println("Request reached in show method");
		return "Welcome to RestFul Project";
	}
	
	@RequestMapping(value = "/employee", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEmployee(@RequestParam("name") String name) {
		System.out.println("Fetching details of: "+name);		
		return new ResponseEntity<Object>(employeeService.getEmployee(name), HttpStatus.OK);

	}

	@RequestMapping(value = "/employee", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public String addEmployee(@RequestBody Employee employee) {
		System.out.println("Adding employee");
		return employeeService.addEmployee(employee);
	}

	@RequestMapping(value = "/employee", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public String updateEmployee(@RequestBody Employee employee) {
		System.out.println("Updating employee");
		return employeeService.updateEmployee(employee);
	}


	@RequestMapping(value = "/employee", method = RequestMethod.DELETE, produces = "application/json")
	public String removeEmployee(@RequestParam("name") String name) {
		System.out.println("Remove employee: "+name);
		return employeeService.removeEmployee(name);
	}

}
