package org.egov.eis.service;

import org.egov.eis.dao.EmployeeDao;
import org.egov.eis.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeDao employeeDao;

	public void saveEmployee(Employee employee) {
		employeeDao.saveEmployee(employee);
		employeeDao.saveAssignments(employee.getAssignments());
		// .....
		
  }
	}
	

