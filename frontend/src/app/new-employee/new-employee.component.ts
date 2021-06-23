import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { Room } from '../classes/Room';
import { Position } from '../classes/Position';
import { Employee } from '../classes/Employee';
import { EmployeeService } from '../employee.service';
import { RoomService } from '../room.service';
import { PositionService } from '../position.service';
import { DeskService } from '../desk.service';
import { MatStepper } from '@angular/material';
import {NgSelectModule, NgOption} from '@ng-select/ng-select';

@Component({
  selector: 'app-new-employee',
  templateUrl: './new-employee.component.html',
  styleUrls: ['./new-employee.component.sass']
})
export class NewEmployeeComponent implements OnInit {

  positions: Position[];
  rooms: Room[];
  room: Room;
  desks = [];
  full: boolean;
  viewBoxTxt: string;
  employees: Employee[];
  employee: Employee;
  employeeToDisplay: Employee;
  positionToDisplay: Position;
  selectedDesk = -1;
  selectedNotTakenDesk = -1;
  lastEmployee: string;


  meterToPixel = 50;
  changed = false;
  min: number;
  max: number;

  public employeeForm = this.fb.group({
    firstName: ['', [Validators.required, Validators.minLength(3)]],
    lastName: ['', [Validators.required, Validators.minLength(3)]],
    salary: ['', Validators.required],
    room: [''],
    position: ['', Validators.required],
  });

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private roomService: RoomService,
    private positionService: PositionService,
    private deskService: DeskService,
  ) { }

  ngOnInit() {
    this.positions = this.positionService.getPositions();
    this.rooms = this.roomService.getRooms();
  }

  findRoomWithName(name: string): number {
    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.rooms.length; i++) {
      if (this.rooms[i].name === name) {
        return this.rooms[i].id;
      }
    }
    return -1;
  }

  findPositionWithName(name: string): number {
    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.positions.length; i++) {
      if (this.positions[i].name === name) {
        return this.positions[i].id;
      }
    }
    return -1;
  }

  addEmployee(employee: Employee) {
    this.employeeService.addEmployee(employee);
  }

  toRoom() {
    this.lastEmployee = null;
    console.log(this.lastEmployee);
    this.employees = this.employeeService.getEmployeesFromRoom(this.room.id);
    this.viewBoxTxt = '0 0 ' + this.room.width * this.meterToPixel + ' ' + this.room.height * this.meterToPixel;
    this.employee = new Employee(
      this.employeeService.getId(),
      this.employeeForm.controls.firstName.value,
      this.employeeForm.controls.lastName.value,
      this.findPositionWithName(this.employeeForm.controls.position.value),
      this.employeeForm.controls.salary.value,
      this.findRoomWithName(this.employeeForm.controls.room.value),
    );
  }

  mouseDownEvent(event) {
    this.findDesk(event.target.id);
    if (this.selectedNotTakenDesk > -1) {
      this.desks[this.selectedNotTakenDesk].taken = true;
      this.desks[this.selectedNotTakenDesk].color = '#3dd2ff';
    }

    if (this.selectedDesk > -1) {
      // tslint:disable-next-line: prefer-for-of
      for (let i = 0; i < this.employees.length; i++) {
        if (this.employees[i].deskId === this.desks[this.selectedDesk].id) {
          this.employeeToDisplay = this.employees[i];
          break;
        }
      }

      // tslint:disable-next-line: prefer-for-of
      for (let i = 0; i < this.positions.length; i++) {
        if (this.employeeToDisplay.positionId === this.positions[i].id) {
          this.positionToDisplay = this.positions[i];
          break;
        }
      }
    }
  }

  findDesk(id: number) {
    if (this.selectedNotTakenDesk > -1) {
      this.desks[this.selectedNotTakenDesk].taken = false;
      this.desks[this.selectedNotTakenDesk].color = '#ad7d1c';
    }

    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.desks.length; i++) {
      // tslint:disable-next-line: triple-equals
      if (this.desks[i].id == id) {
        if (this.desks[i].taken) {
          this.selectedNotTakenDesk = -1;
          this.selectedDesk = i;
        } else {
          this.selectedDesk = -1;
          this.selectedNotTakenDesk = i;
        }
        return;
      }
    }
    this.selectedNotTakenDesk = -1;
    this.selectedDesk = -1;
  }

  backClick() {
    if (this.selectedNotTakenDesk > -1) {
      this.desks[this.selectedNotTakenDesk].taken = false;
      this.desks[this.selectedNotTakenDesk].color = '#ad7d1c';
      this.selectedNotTakenDesk = -1;
    }
  }

  onSubmit(stepper: MatStepper) {
    this.employee.deskId = this.desks[this.selectedNotTakenDesk].id;
    this.positionService.incrementPopulation(this.findPositionWithName(this.employeeForm.controls.position.value));
    this.roomService.incrementPopulation(this.findRoomWithName(this.employeeForm.controls.room.value));
    this.addEmployee(this.employee);
    this.desks[this.selectedNotTakenDesk].color = '#ffc021';
    this.deskService.updateDesks(this.desks);
    this.lastEmployee = this.employee.firstName + ' ' + this.employee.lastName;

    this.selectedDesk = -1;
    this.selectedNotTakenDesk = -1;

    stepper.reset();
    this.employeeForm.reset();
  }

  onRoomChange() {
    this.room = this.roomService.getRoomO(this.findRoomWithName(this.employeeForm.controls.room.value));
    const capacity = this.room.capacity;
    const population = this.room.population;
    this.desks = this.deskService.getSpecifiedDesks(this.room.desksID);

    population < capacity ? this.full = false : this.full = true;

    if (this.full) {
      this.employeeForm.controls.room.setErrors({ incorrect: true });
    } else {
      this.employeeForm.controls.room.setErrors(null);
    }
  }

  onPositionChange() {
    const position = this.positionService.getPositionO(this.findPositionWithName(this.employeeForm.controls.position.value));
    console.log(this.findPositionWithName(this.employeeForm.controls.position.value));
    this.min = position.minWage;
    position.maxWage == null || '' ? this.max = -1 : this.max = position.maxWage;
    if (this.max > 0) {
      // tslint:disable-next-line: max-line-length
      this.employeeForm.get('salary').setValidators([Validators.min(position.minWage), Validators.max(position.maxWage), Validators.required]);
    } else {
      this.employeeForm.get('salary').setValidators([Validators.min(position.minWage), Validators.required]);

    }
    this.changed = true;
  }
}
