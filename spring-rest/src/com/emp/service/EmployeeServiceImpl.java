package com.emp.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.emp.dao.Employee;

public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public String addEmployee(Employee employee) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.save(employee);
			transaction = session.beginTransaction();
			transaction.commit();
		} catch (Exception exception) {
			transaction.rollback();
			System.out.println("Exception occured in adding employoee: " + exception);
			return "Something went wrong!";
		} finally {
			session.close();
		}
		return "Employee added";
	}

	@Override
	public Object getEmployee(String employeeName) {
		Session session = null;
		Employee employee = null;
		try {
		session = sessionFactory.openSession();
		String sql = "from Employee where name=:employeeName";
		Query query = session.createQuery(sql);
		query.setParameter("employeeName", employeeName);
		employee = (Employee) query.uniqueResult();
		}
		catch (Exception exception){
			exception.printStackTrace();
			return "Something went wrong, please contact support";
		}		
		finally {
			session.close();
		}
		
		if(employee==null) {
			System.out.println("Employee doesn't exist!");
			return "Employee doesn't exist!";
			}
		else
		return employee;
	}

	@Override
	public String removeEmployee(String employeeName) {
		Session session = sessionFactory.openSession();
		Object employee = getEmployee(employeeName);
		if (employee != null) {
			String sql = "delete from Employee where name=:employeeName";
			Query deleteQuery = session.createQuery(sql);
			deleteQuery.setParameter("employeeName", employeeName);
			int no_of_record_deleted = deleteQuery.executeUpdate();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				transaction.commit();
			} catch (Exception exception) {
				transaction.rollback();
				System.out.println("Error in removing record");
			} finally {
				session.close();
			}
			if (no_of_record_deleted > 0)
				return "Employee removed ";
			else
				return "Employee couldn't removed";
		}
		return "Employee: " + employeeName + " doesn't exist";
	}
	
	public String updateEmployee(Employee emp) {
		Session session = sessionFactory.openSession();
		Object object = getEmployee(emp.getName());
		Employee employee = (Employee)object;
		
		if(employee!=null) {			
			  employee.setName(emp.getName());
			  employee.setBusinessUnit(emp.getBusinessUnit());
			  employee.setDepartment(emp.getDepartment());
			  employee.setExperience(emp.getExperience());
			  employee.setLocation(emp.getLocation());			 
			  session.update(employee);
			  
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				transaction.commit();
			} catch (Exception exception) {
				transaction.rollback();
				System.out.println("Error in updating record");
				return "Something went wrong!";
			} finally {
				session.close();
			}
			return "Employee Updated";
		}
		else
			return "Employee "+ emp.getName() +" doesn't exist!";
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
