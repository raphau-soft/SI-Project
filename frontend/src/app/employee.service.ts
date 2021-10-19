import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const API = "http://localhost:8080/employee";
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

import { Employee } from './classes/Employee';
import { DeskService } from './desk.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(
    private deskService: DeskService,
    private http: HttpClient
  ) { }

  getEmployee(id: number): Observable<any> {
    return this.http.get(API + "/" + id, {responseType: 'text'});
  }

  getEmployeesByBuildingId(id): Observable<any>{
    return this.http.get(API + "/building/" + id, { responseType: 'text' });
  }

  deleteEmployeeById(id): Observable<any>{
    return this.http.delete(API + "/" + id);
  }
  
  getEmployeesByRoomId(id): Observable<any>{
    return this.http.get(API + "/room/" + id, {responseType: 'text'});
  }

  postEmployee(employee):Observable<any>{
    return this.http.post(API, employee, httpOptions);
  }

  getEmployees(): Employee[] {
    const employees = JSON.parse(localStorage.getItem('employees'));
    return employees == null ? [] : employees.employees;
  }

  getEmployeesFromRoom(roomId: number): Employee[] {
    let employees = this.getEmployees();
    employees = employees.filter(employee => employee.room.id === roomId);
    return employees;
  }

  getMaxSalary() {
    const employees = this.getEmployees();
    if (employees.length > 0) {
      let max = employees[0].salary;
      for (let i = 1; i < employees.length; i++) {
        if (max < employees[i].salary) {
          max = employees[i].salary;
        }
      }
      return max;
    }
    return 0;
  }

  getMinSalary() {
    const employees = this.getEmployees();
    if (employees.length > 0) {
      let min = employees[0].salary;
      for (let i = 1; i < employees.length; i++) {
        if (min > employees[i].salary) {
          min = employees[i].salary;
        }
      }
      return min;
    }
    return 0;
  }

}
