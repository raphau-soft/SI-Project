import { Injectable } from '@angular/core';
import { RoomService } from './room.service';
import { PositionService } from './position.service';
import { EmployeeService } from './employee.service';


@Injectable({
  providedIn: 'root'
})
export class SummaryService {

  getPositions() {
    return this.positionService.getPositions();
  }
  getRooms() {
    return this.roomService.getRooms();
  }
  getEmployees() {
    return this.employeeService.getEmployees();
  }

  constructor(
    private roomService: RoomService,
    private positionService: PositionService,
    private employeeService: EmployeeService,
  ) { }

  getEmployeesNumber(): number{
    return this.employeeService.getEmployees().length;
  }

  getEmployeesAtPosition(id: number): number{
    let employees = this.employeeService.getEmployees();
    employees = employees.filter(employee => employee.positionId === id);
    return employees.length;
  }

  getTotalSpendings(){
    const employees = this.employeeService.getEmployees();
    let spendings = 0;
    for(var i = 0; i< employees.length;i++){
      spendings += employees[i].salary;
    }
    return spendings;
  }

  getRoomsNumber(){
    return this.roomService.getRooms().length;
  }

  getPositionsNumber(){
    return this.positionService.getPositions().length;
  }
}
