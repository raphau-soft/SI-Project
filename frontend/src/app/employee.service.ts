import { Injectable } from '@angular/core';

import { Employee } from './classes/Employee';
import { DeskService } from './desk.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(
    private deskService: DeskService
  ) { }

  getEmployees(): Employee[] {
    const employees = JSON.parse(localStorage.getItem('employees'));
    return employees == null ? [] : employees.employees;
  }

  getEmployeesFromRoom(roomId: number): Employee[] {
    let employees = this.getEmployees();
    employees = employees.filter(employee => employee.roomId === roomId);
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

  getEmployee(id: number) {
    let employees = this.getEmployees();
    employees = employees.filter(employee => employee.id === id);
    return employees[0];
  }

  addEmployee(employee: Employee): void {
    const employees = this.getEmployees();
    employees.push(employee);
    this.sendToDatabase(employees);
  }

  getId(): number {
    const employees = this.getEmployees();
    const maxId = employees.length === 0 ? 0 : employees[employees.length - 1].id;
    return maxId + 1;
  }

  removeEmp(id: number) {
    const emp = this.getEmployee(id);
    let employees = this.getEmployees();
    employees = employees.filter(employee => employee.id !== id);
    this.deskService.freeDesk(emp.deskId);
    this.sendToDatabase(employees);
  }

  sendToDatabase(employees: Employee[]) {
    employees = employees.sort((n1, n2) => {
      if (n1.id > n2.id) {
        return 1;
      }
      if (n1.id < n2.id) {
        return -1;
      }
      return 0;
    });

    localStorage.setItem('employees', JSON.stringify({ employees }));
  }

  updateEmployee(employee: Employee) {
    this.removeEmp(employee.id);
    this.addEmployee(employee);
  }
}