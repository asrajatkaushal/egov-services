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

package org.egov.eis.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.egov.eis.model.EmployeeType;
import org.egov.eis.service.EmployeeTypeService;
import org.egov.eis.web.contract.EmployeeTypeGetRequest;
import org.egov.eis.web.contract.EmployeeTypeResponse;
import org.egov.eis.web.contract.ResponseInfo;
import org.egov.eis.web.contract.factory.ResponseInfoFactory;
import org.egov.eis.web.errorhandlers.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employeetypes")
public class EmployeeTypeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeTypeController.class);

	@Autowired
	private EmployeeTypeService employeeTypeService;

	@Autowired
	private ErrorHandler errHandler;

	@Autowired
	private ResponseInfoFactory responseInfoFactory;

	@GetMapping(headers = { "apiId", "ver", "ts", "action", "did", "msgId", "requesterId", "authToken" })
	@ResponseBody
	public ResponseEntity<?> search(@ModelAttribute @Valid EmployeeTypeGetRequest employeeTypeGetRequest,
			BindingResult bindingResult, @RequestHeader HttpHeaders headers) {
		logger.debug("Inside search() of EmployeeTypeController" + headers);

		// validate header
		if(headers.getFirst("apiId") == null || headers.getFirst("ver") == null || headers.getFirst("ts") == null ) {
			return errHandler.getErrorResponseEntityForMissingHeaders(headers);
		}

		// validate input params
		if (bindingResult.hasErrors()) {
			return errHandler.getErrorResponseEntityForMissingParametes(bindingResult, headers);
		}

		// Call service
		List<EmployeeType> employeeTypesList = null;
		try {
			employeeTypesList = employeeTypeService.getEmployeeTypes(employeeTypeGetRequest);
		} catch (Exception exception) {
			logger.error("Error while processing request " + employeeTypeGetRequest, exception);
			return errHandler.getResponseEntityForUnexpectedErrors(headers);
		}

		return getSuccessResponse(employeeTypesList, headers);
	}

	/**
	 * Populate Response object and returnemployeeTypesList
	 * 
	 * @param employeeTypesList
	 * @return
	 */
	private ResponseEntity<?> getSuccessResponse(List<EmployeeType> employeeTypesList, HttpHeaders headers) {
		EmployeeTypeResponse employeeTypeRes = new EmployeeTypeResponse();
		employeeTypeRes.setEmployeeType(employeeTypesList);
		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestHeaders(headers, true);
		responseInfo.setStatus(HttpStatus.OK.toString());
		employeeTypeRes.setResponseInfo(responseInfo);
		return new ResponseEntity<EmployeeTypeResponse>(employeeTypeRes, HttpStatus.OK);

	}

}