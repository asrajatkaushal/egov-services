/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) 2016  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.eis.service;

import java.util.ArrayList;
import java.util.List;

import org.egov.eis.broker.EmployeeProducer;
import org.egov.eis.model.Employee;
import org.egov.eis.model.EmployeeInfo;
import org.egov.eis.model.UserInfo;
import org.egov.eis.repository.EmployeeRepository;
import org.egov.eis.service.helper.EmployeeUserMapper;
import org.egov.eis.web.contract.EmployeeGetRequest;
import org.egov.eis.web.contract.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private EmployeeUserMapper employeeUserMapper;
	
	@Autowired
	EmployeeProducer employeeProducer;
	
	@Value("${kafka.topics.employee.savedb.name}")
	String employeeSaveTopic;
	
	@Value("${kafka.topics.employee.savedb.key}")
	String employeeSaveKey;

	public List<EmployeeInfo> getEmployees(EmployeeGetRequest employeeGetRequest) {
		List<EmployeeInfo> employeeInfoList = employeeRepository.findForCriteria(employeeGetRequest);
/*
 		List<UserInfo> userInfoList = userService.getUsers(employeeGetRequest);
*/
		List<UserInfo> userInfoList = new ArrayList<>();
		employeeUserMapper.mapUsersWithEmployees(employeeInfoList, userInfoList);

		return employeeInfoList;
	}

	public void createEmployee(Employee employee) {
		// create user For Employee by REST API calland get the Id for the user.
		UserRequest userRequest = getUserRequest(employee);
		userService.createUser(userRequest);
		Long id = 100L;
		employee.setId(id);
		// get code generated for employee by calling employee code generator service
		String code = getNewEmployeeCode();
		employee.setCode(code);
		
		String employeeJson = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			employeeJson = mapper.writeValueAsString(employee);
			LOGGER.info("employeeJson::" + employeeJson);
		} catch (JsonProcessingException e) {
			LOGGER.error("Error while converting Employee to JSON", e);
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		try {
			employeeProducer.sendMessage(employeeSaveTopic, employeeSaveKey, employeeJson);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

	private String getNewEmployeeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	private UserRequest getUserRequest(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}