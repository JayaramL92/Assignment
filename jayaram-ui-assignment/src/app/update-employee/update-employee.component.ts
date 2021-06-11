import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../employee.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit {

  employeeUpdateForm: FormGroup;
  employeeId: number;

  constructor(private route: ActivatedRoute, private employeeService: EmployeeService, private router: Router) { 
    
  }

  ngOnInit(): void {
    this.employeeId = this.route.snapshot.params['id'];

    this.employeeService.getEmployee(this.employeeId)
      .subscribe(data => {
        console.log(data)
        this.employeeUpdateForm = new FormGroup({
          'employeeFormData': new FormGroup({
            'firstName': new FormControl(data.firstName, [Validators.required,Validators.nullValidator]),
            'lastName': new FormControl(data.lastName, [Validators.required,Validators.nullValidator]),
            'phoneNumber': new FormControl(data.phoneNumber, [Validators.required,Validators.maxLength(10)]),
            'emailId': new FormControl(data.emailId, [Validators.required, Validators.email]),
            'employeeId': new FormControl(data.employeeId, null)
          })
        });
      }, error => console.log(error));
  }

  updateEmployee() {
    this.employeeService.updateEmployee(this.employeeUpdateForm.value.employeeFormData)
      .subscribe(data => {
        console.log(data);
        alert(this.employeeUpdateForm.value.employeeFormData.firstName+" Updated Successfully");
        this.gotoList();
      }, error => console.log(error));
  }

  onSubmit() {
    this.updateEmployee();
  }

  gotoList() {
    this.router.navigate(['employees']);
  }

}
