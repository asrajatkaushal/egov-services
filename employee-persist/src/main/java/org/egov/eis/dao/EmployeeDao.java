package org.egov.eis.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.egov.eis.model.Assignment;
import org.egov.eis.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDao {

	public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);

	// FIXME Employee sequence
	public static final String INSERT_EMPLOYEE_QUERY = "INSERT INTO egeis_employee(id, code, dateOfAppointment,"
			+ " dateofjoining, dateofretirement, employeestatus, recruitmentmodeId, recruitmenttypeId,"
			+ " recruitmentquotaId, retirementage, dateofresignation, dateoftermination, employeetypeId,"
			+ " mothertongueId, religionId, communityId, categoryId, physicallydisabled, medicalreportproduced,"
			+ " maritalstatus, passportno, gpfno, bankId, bankbranchId, bankaccount, groupId, placeofbirth, tenantId)"
			+ " VALUES (NEXTVAL('seq_egeis_employee'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	// Add AssignmentQuery
	// ServiceHistoryQuery...
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// FIXME put tenantId
	public void saveEmployee(Employee employee) {
		System.out.println(employee);
		Object[] obj = new Object[] { employee.getCode(), employee.getDateOfAppointment(), employee.getDateOfJoining(),
				employee.getDateOfRetirement(), employee.getEmployeeStatus(), employee.getRecruitmentMode(),
				employee.getRecruitmentType(), employee.getRecruitmentQuota(), employee.getRetirementAge(),
				employee.getDateOfResignation(), employee.getDateOfTermination(), employee.getEmployeeType(),
				employee.getMotherTongue(), employee.getReligion(), employee.getCommunity(), employee.getCategory(),
				employee.getPhysicallyDisabled(), employee.getMedicalReportProduced(),
				employee.getMaritalStatus().toString(), employee.getPassportNo(), employee.getGpfNo(),
				employee.getBank(), employee.getBankBranch(), employee.getBankAccount(), employee.getGroup(),
				employee.getPlaceOfBirth(), "1" };
		jdbcTemplate.update(INSERT_EMPLOYEE_QUERY, obj);
	}

	public void saveAssignments(List<Assignment> assignments) {

		String sql = "INSERT INTO egeis_assignment "
				+ "(id, employeeId, positionId, fundId, functionaryId, functionId, departmentId, designationId,"
				+ " isprimary, fromdate, todate, gradeId, govtordernumber, createdby, createddate,"
				+ " lastmodifiedby, lastmodifieddate, tenantId"
				+ ") VALUES (NEXTVAL('seq_egeis_assignment'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Assignment assignment = assignments.get(i);
				ps.setLong(1, assignment.getEmployee());
				ps.setLong(2, assignment.getPosition());
				ps.setLong(3, assignment.getFund());
				ps.setLong(4, assignment.getFunctionary());
				ps.setLong(5, assignment.getFunction());
				ps.setLong(6, assignment.getDepartment());
				ps.setLong(7, assignment.getDesignation());
				ps.setBoolean(8, assignment.getIsPrimary());
				ps.setDate(9, new Date(assignment.getFromDate().getTime()));
				ps.setDate(10, new Date(assignment.getToDate().getTime()));
				ps.setLong(11, assignment.getGrade());
				ps.setString(12, assignment.getGovtOrderNumber());
				ps.setLong(13, assignment.getCreatedBy());
				ps.setDate(14, new Date(assignment.getCreatedDate().getTime()));
				ps.setLong(15, assignment.getLastModifiedBy());
				ps.setDate(16, new Date(assignment.getLastModifiedDate().getTime()));
				ps.setString(17, "1");
			}

			@Override
			public int getBatchSize() {
				return assignments.size();
			}
		});

	}

	// .... servicehistory, test...

}
