package com.boa.jayaram.assignment.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boa.jayaram.assignment.bean.Employee;
import com.boa.jayaram.assignment.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployee() {
		try {
			return employeeRepository.findAll();
		} catch (Exception ex) {
			log.error("Error while fetching the employee details - due to :: ", ex);
			return Collections.emptyList();
		}
	}

	public Employee getEmployeeById(Long employeeId) {
		try {
			Optional<Employee> tempEmployee = employeeRepository.findById(employeeId);

			if (!tempEmployee.isPresent()) {
				log.error("Unable to find Employee with Employee Id :: {}", employeeId);
			}

			return tempEmployee.get();
		} catch (Exception ex) {
			log.error("Error while fetching the employee details - due to :: ", ex);
			return null;
		}
	}

	public boolean saveEmployee(Employee employee) {
		employeeRepository.save(employee);
		log.info("{} - Saved successfully", employee);
		return true;
	}

	public String updateEmployee(Employee employee) {
		StringBuilder status = new StringBuilder();

		Optional<Employee> tempEmployee = employeeRepository.findById(employee.getEmployeeId());

		if (!tempEmployee.isPresent()) {
			log.error("Unable to find Employee with FirstName :: {}", employee.getFirstName());
			status.append("Unable to find Employee with FirstName ").append(employee.getFirstName())
					.append(" to update");
			return status.toString();
		}

		employee.setEmployeeId(tempEmployee.get().getEmployeeId());
		employeeRepository.save(employee);

		log.info("{} - Updated successfully", employee);
		status.append("Employee - ").append(employee.getFirstName()).append(" -  Updated successfully");
		return status.toString();
	}

	public String deleteEmployee(Long employeeId) {
		StringBuilder status = new StringBuilder();

		Optional<Employee> tempEmployee = employeeRepository.findById(employeeId);

		if (!tempEmployee.isPresent()) {
			log.error("Unable to find Employee with Employee Id :: {}", employeeId);
			status.append("Unable to find Employee with Employee Id ").append(employeeId).append(" to delete");
			return status.toString();
		}

		employeeRepository.delete(tempEmployee.get());

		log.info("Employee - {} - Deleted successfully", employeeId);
		status.append("Employee - ").append(employeeId).append(" -  Deleted successfully");
		return status.toString();
	}

}
