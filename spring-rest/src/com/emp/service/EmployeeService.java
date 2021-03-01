package com.emp.service;

import com.emp.dao.Employee;

public interface EmployeeService {

	String addEmployee(Employee employee);
	Object getEmployee(String name);
	String removeEmployee(String name);	
	String updateEmployee(Employee employee);
}
