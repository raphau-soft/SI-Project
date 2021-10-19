import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Employee } from '../classes/Employee';
import { EmployeeService } from '../employee.service';
import { FormBuilder, Validators } from '@angular/forms';
import { RoomService } from '../room.service';
import { PositionService } from '../position.service';
import { Room } from '../classes/Room';
import { Position } from '../classes/Position';
import { DeskService } from '../desk.service';
import { Desk } from '../classes/Desk';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.sass']
})
export class UpdateEmployeeComponent implements OnInit {

  positions: Position[];
  oldRoomId: number;
  oldPositionId: number;
  oldDesk = -1;
  oldDeskId: number;
  rooms: Room[];
  room: Room;
  full: boolean;
  viewBoxTxt: string;
  employees: Employee[];
  employeeToDisplay: Employee;
  positionToDisplay: Position;
  selectedDesk = -1;
  selectedNotTakenDesk = -1;
  desks = [];
  meterToPixel = 50;

  employeeFromDb: Employee;
  employee: Employee;
  id: number;

  changed = false;
  min: number;
  max: number;

  constructor(
    private deskService: DeskService,
    private route: ActivatedRoute,
    private location: Location,
    private employeeService: EmployeeService,
    private fb: FormBuilder,
    private roomService: RoomService,
    private positionService: PositionService,
  ) { }

  public employeeForm = this.fb.group({
    firstName: ['', [Validators.required, Validators.minLength(3)]],
    lastName: ['', [Validators.required, Validators.minLength(3)]],
    salary: [0, Validators.required],
    room: [''],
    position: [''],
  });

  ngOnInit() {
    this.id = +this.route.snapshot.paramMap.get('id');
    this.employeeService.getEmployee(this.id).subscribe(
      data => {
        this.employeeFromDb = JSON.parse(data);
        this.employeeForm.controls.firstName.setValue(this.employeeFromDb.firstName);
        this.employeeForm.controls.lastName.setValue(this.employeeFromDb.lastName);
        this.employeeForm.controls.salary.setValue(this.employeeFromDb.salary);

        this.positionService.getPositionsByBuildingId(this.employeeFromDb.buildingId).subscribe(
          data => {
            this.positions = JSON.parse(data);
          }
        );
        this.roomService.getRoomsByBuildingId(this.employeeFromDb.buildingId).subscribe(
          data => {
            this.rooms = JSON.parse(data);
          }
        );
      },
      error => {
        console.log(error);
      }
    );

    // this.positions = this.positionService.getPositions();
    // this.rooms = this.roomService.getRooms();
    // this.getEmployee();
  }

  getEmployee() {
    // const id = +this.route.snapshot.paramMap.get('id');
    // this.employee = this.employeeService.getEmployee(id);
    // this.room = this.roomService.getRoomO(this.employee.roomId);
    // this.employeeForm.patchValue({
    //   firstName: this.employee.firstName,
    //   lastName: this.employee.lastName,
    //   salary: this.employee.salary,
    // });
    // this.oldRoomId = this.employee.roomId;
    // this.oldPositionId = this.employee.positionId;
    // this.oldDeskId = this.employee.deskId;
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

  onSubmit() {

    // this.roomService.decrementPopulation(this.oldRoomId);
    // this.positionService.decrementPopulation(this.oldPositionId);

    // this.positionService.incrementPopulation(this.employee.positionId);
    // this.roomService.incrementPopulation(this.employee.roomId);

    // this.deskService.freeDesk(this.oldDeskId);

    // if (this.oldDesk > -1 ) {
    //   this.desks[this.oldDesk].color = '#ad7d1c';
    // }

    // this.desks[this.selectedNotTakenDesk].color = '#ffc021';


    // this.employee.deskId = this.desks[this.selectedNotTakenDesk].id;

    // this.employeeService.updateEmployee(this.employee);
    // this.deskService.updateDesks(this.desks);
    // this.location.back();
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
            tempDesks[i].positionX, tempDesks[i].positionY, tempDesks[i].rotation, tempDesks[i].taken, tempDesks[i].color));
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

  findRoom(id: number): Room {
    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.rooms.length; i++) {
      if (this.rooms[i].id === id) {
        return this.rooms[i];
      }
    }
    return null;
  }

  onPositionChange() {
    const position = this.positionService.getPositionO(this.findPositionWithName(this.employeeForm.controls.position.value));
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

  toRoom() {
    if(this.room != undefined){
      this.employeeService.getEmployeesByRoomId(this.room.id).subscribe(
        data => {
          this.employees = JSON.parse(data);
        }
      )
      this.viewBoxTxt = '0 0 ' + this.room.width * this.meterToPixel + ' ' + this.room.height * this.meterToPixel;
      this.employee = new Employee(
        this.employeeFromDb.id,
        this.employeeForm.controls.firstName.value,
        this.employeeForm.controls.lastName.value,
        this.employeeForm.controls.salary.value,
      );
      this.employee.buildingId = this.employeeFromDb.buildingId;
      this.employee.positionId = this.findPositionWithName(this.employeeForm.controls.position.value);
      this.employee.roomId = this.findRoomWithName(this.employeeForm.controls.room.value);
      console.log(this.employee);
    }
  }

  mouseDownEvent(event) {
    // this.findDesk(event.target.id);
    // if (this.selectedNotTakenDesk > -1) {
    //   console.log(this.desks[this.selectedNotTakenDesk].id);
    //   this.desks[this.selectedNotTakenDesk].taken = true;
    //   this.desks[this.selectedNotTakenDesk].color = '#3dd2ff';
    // }

    // if (this.selectedDesk > -1) {
    //   // tslint:disable-next-line: prefer-for-of
    //   for (let i = 0; i < this.employees.length; i++) {
    //     if (this.employees[i].deskId === this.desks[this.selectedDesk].id) {
    //       this.employeeToDisplay = this.employees[i];
    //       break;
    //     }
    //   }

    //   // tslint:disable-next-line: prefer-for-of
    //   for (let i = 0; i < this.positions.length; i++) {
    //     if (this.employeeToDisplay.positionId === this.positions[i].id) {
    //       this.positionToDisplay = this.positions[i];
    //       break;
    //     }
    //   }
    // }
  }

  findDesk(id: number) {
    if (this.selectedNotTakenDesk > -1) {
      this.desks[this.selectedNotTakenDesk].taken = false;
      // tslint:disable-next-line: triple-equals
      if (this.selectedNotTakenDesk != this.oldDesk) {
        this.desks[this.selectedNotTakenDesk].color = '#ad7d1c';
      }
    }

    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.desks.length; i++) {
      // tslint:disable-next-line: triple-equals
      if (this.desks[i].id == id) {
        if (this.desks[i].taken) {
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

}
