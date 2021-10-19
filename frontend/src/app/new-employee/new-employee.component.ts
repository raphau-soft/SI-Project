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
import { ActivatedRoute, Router } from '@angular/router';
import {NgSelectModule, NgOption} from '@ng-select/ng-select';
import { Desk } from '../classes/Desk';

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
  companyId: number;

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
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
    this.companyId = +this.route.snapshot.paramMap.get('id');
    this.positionService.getPositionsByCompanyId(this.companyId).subscribe(
      data => {
        this.positions = JSON.parse(data);
      }
    );
    this.roomService.getRoomsByCompanyId(this.companyId).subscribe(
      data => {
        this.rooms = JSON.parse(data);
      }
    );
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

  findPosition(id: number): Position {
    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.positions.length; i++) {
      if (this.positions[i].id === id) {
        return this.positions[i];
      }
    }
    return null;
  }

  findRoom(id: number): Room {
    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.rooms.length; i++) {
      if (this.rooms[i].id === id) {
        return this.rooms[i];
      }
    }
    return null;
  }

  toRoom() {
    this.lastEmployee = null;
    this.employeeService.getEmployeesByRoomId(this.room.id).subscribe(
      data => {
        this.employees = JSON.parse(data);
      }
    )
    this.viewBoxTxt = '0 0 ' + this.room.width * this.meterToPixel + ' ' + this.room.height * this.meterToPixel;
    this.employee = new Employee(
      0,
      this.employeeForm.controls.firstName.value,
      this.employeeForm.controls.lastName.value,
      this.employeeForm.controls.salary.value,
    );
    this.employee.positionId = this.findPositionWithName(this.employeeForm.controls.position.value);
    this.employee.roomId = this.findRoomWithName(this.employeeForm.controls.room.value);
  }

  mouseDownEvent(event) {
    this.findDesk(event.target.id);
    if (this.selectedNotTakenDesk > -1) {
      this.desks[this.selectedNotTakenDesk].taken = true;
      this.desks[this.selectedNotTakenDesk].color = '#3dd2ff';
    }

    if (this.selectedDesk > -1) {
      //tslint:disable-next-line: prefer-for-of
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
    this.employee.companyId = this.companyId;
    this.employeeService.postEmployee(this.employee).subscribe(
      data => {
        this.lastEmployee = this.employee.firstName + ' ' + this.employee.lastName;
        this.selectedDesk = -1;
        this.selectedNotTakenDesk = -1;
        stepper.reset();
        this.employeeForm.reset();
      }
    );
  }

  onRoomChange() {
    this.room = this.findRoom(this.findRoomWithName(this.employeeForm.controls.room.value));
    const capacity = this.room.capacity;
    const population = this.room.population;
    this.desks = [];
    this.deskService.getDesksByRoomId(this.room.id).subscribe(
      data => {
        let tempDesks = JSON.parse(data);
        for(let i = 0; i < tempDesks.length; i++){
          this.desks.push(new Desk(tempDesks[i].width, tempDesks[i].height, tempDesks[i].id, 
            tempDesks[i].positionX, tempDesks[i].positionY, tempDesks[i].rotation, tempDesks[i].color));
        }
      }
    );

    population < capacity ? this.full = false : this.full = true;

    if (this.full) {
      this.employeeForm.controls.room.setErrors({ incorrect: true });
    } else {
      this.employeeForm.controls.room.setErrors(null);
    }
  }

  onPositionChange() {
    const position = this.findPosition(this.findPositionWithName(this.employeeForm.controls.position.value));
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
