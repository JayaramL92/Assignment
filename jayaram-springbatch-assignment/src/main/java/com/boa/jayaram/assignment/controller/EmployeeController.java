package com.boa.jayaram.assignment.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.boa.jayaram.assignment.bean.Employee;
import com.boa.jayaram.assignment.bean.GeneralResponse;
import com.boa.jayaram.assignment.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		log.info("Test url successfull!");
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		try {
			return new ResponseEntity<>(employeeService.getAllEmployee(), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error while getting Employee list :: {}", ex);
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
		try {
			return new ResponseEntity<>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error while getting Employee list :: {}", ex);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<GeneralResponse> addEmployee(@RequestBody Employee employee) {
		try {
			employeeService.saveEmployee(employee);
			GeneralResponse response = new GeneralResponse(HttpStatus.OK.value(), "Employee Created");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error while Saving Employee - {} :: {}", employee.getFirstName(), ex);
			return new ResponseEntity<>(
					new GeneralResponse(HttpStatus.OK.value(),
							"Employee -" + employee.getFirstName() + " Creation failed"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<GeneralResponse> updateEmployee(@RequestBody Employee employee) {
		try {
			String status = employeeService.updateEmployee(employee);
			GeneralResponse response = new GeneralResponse(HttpStatus.OK.value(), status);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error while Updating Employee - {} :: {}", employee.getFirstName(), ex);
			return new ResponseEntity<>(new GeneralResponse(HttpStatus.OK.value(), "Employee Updation failed"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{employeeId}")
	public ResponseEntity<GeneralResponse> deleteEmployee(@PathVariable Long employeeId) {
		try {
			String status = employeeService.deleteEmployee(employeeId);
			GeneralResponse response = new GeneralResponse(HttpStatus.OK.value(), status);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error while Deleting Employee - {} :: {}", employeeId, ex);
			return new ResponseEntity<>(new GeneralResponse(HttpStatus.OK.value(), "Employee Deletion failed"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
