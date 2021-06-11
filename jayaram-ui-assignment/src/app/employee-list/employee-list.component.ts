import { Component, OnInit } from '@angular/core';
import { EmployeeDetailsComponent } from '../employee-details/employee-details.component';
import { Observable } from "rxjs";
import { EmployeeService } from "../employee.service";
import { Employee } from "../employee";
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  employees: Observable<Employee[]>;

  constructor(private employeeService: EmployeeService,
    private router: Router) { }

  ngOnInit(): void {
    this.reloadData();
  }

  reloadData() {
    this.employees = this.employeeService.getEmployeesList();
  }

  deleteEmployee(employeeId: number) {
    this.employeeService.deleteEmployee(employeeId)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
          alert("Employee Deleted Successfully");
        },
        error => console.log(error));
  }

  employeeDetails(employeeId: number) {
    this.router.navigate(['details', employeeId]);
  }

  updateDetails(employeeId: number) {
    this.router.navigate(['update', employeeId]);
  }

}