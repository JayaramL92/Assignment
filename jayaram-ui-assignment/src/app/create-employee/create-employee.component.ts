import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-employee',
  templateUrl: './create-employee.component.html',
  styleUrls: ['./create-employee.component.css']
})
export class CreateEmployeeComponent implements OnInit {

  employeeForm: FormGroup;

  constructor(private employeeService: EmployeeService,
    private router: Router) { }

  ngOnInit(): void {
    this.employeeForm = new FormGroup({
      'employeeFormData': new FormGroup({
        'firstName': new FormControl(null, [Validators.required,Validators.nullValidator]),
        'lastName': new FormControl(null, [Validators.required,Validators.nullValidator]),
        'phoneNumber': new FormControl(null, [Validators.required,Validators.maxLength(10)]),
        'emailId': new FormControl(null, [Validators.required, Validators.email])
      })
    });
  }

  save() {
    this.employeeService
      .createEmployee(this.employeeForm.value.employeeFormData).subscribe(data => {
        alert("Employee "+this.employeeForm.value.employeeFormData.firstName+" Created Successfully");
        console.log(data)
        this.gotoList();
      },
      error => console.log(error));
  }

  onSubmit() {
    this.save();
  }

  gotoList() {
    this.router.navigate(['/employees']);
  }

}
