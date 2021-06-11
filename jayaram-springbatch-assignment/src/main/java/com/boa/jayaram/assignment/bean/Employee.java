package com.boa.jayaram.assignment.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLOYEE_DETAILS")
public class Employee {

	@Id
	@Column(name = "EMPLOYEE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "PHONE_NUMBER")
	private Long phoneNumber;

}
