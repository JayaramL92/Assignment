package com.boa.jayaram.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boa.jayaram.assignment.bean.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
