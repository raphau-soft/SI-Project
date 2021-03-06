import { Injectable } from '@angular/core';

import { EmployeeService } from './employee.service';
import { PositionService } from './position.service';
import { RoomService } from './room.service';

@Injectable({
  providedIn: 'root'
})
export class TableService {

  constructor(
    private roomService: RoomService,
    private positionService: PositionService,
    private employeeService: EmployeeService,
  ) { }

  getRooms() {
    return null;
  }

  getRoom(id: number): string {
    return null;
  }

  getPosition(id: number): string {
    return this.positionService.getPosition(id);
  }

  getPositions() {
    return null;
  }

  getEmployees() {
    return this.employeeService.getEmployees();
  }

  removeEmp(id: number) {
    // this.positionService.decrementPopulation(this.employeeService.getEmployee(id).positionId)
    // this.roomService.decrementPopulation(this.employeeService.getEmployee(id).roomId);
    // this.employeeService.removeEmp(id);
  }
}
