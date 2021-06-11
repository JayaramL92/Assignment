import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getEmployee(employeeId: number): Observable<any> {
    console.log(`${this.baseUrl}`+"/employee/"+`${employeeId}`);
    return this.http.get(`${this.baseUrl}`+"/employee/"+`${employeeId}`);
  }

  createEmployee(employee: Object): Observable<Object> {
    console.log(`${this.baseUrl}`);
    return this.http.post(`${this.baseUrl}`+"/add", employee);
  }

  updateEmployee(employee: Object): Observable<Object> {
    console.log(`${this.baseUrl}`+"/update");
    return this.http.put(`${this.baseUrl}`+"/update", employee);
  }

  deleteEmployee(employeeId: number): Observable<any> {
    console.log(`${this.baseUrl}/${employeeId}`);
    return this.http.delete(`${this.baseUrl}`+"/delete/"+`${employeeId}`);
  }

  getEmployeesList(): Observable<any> {
    console.log(`${this.baseUrl}`+"/employees");
    return this.http.get(`${this.baseUrl}`+"/employees");
  }
  
}
