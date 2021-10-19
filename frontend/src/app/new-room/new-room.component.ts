import { Component, OnInit } from '@angular/core';
import { Room } from '../classes/Room';
import { RoomService } from '../room.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Desk } from '../classes/Desk';
import { DeskService } from '../desk.service';
import { MatStepper } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-new-room',
  templateUrl: './new-room.component.html',
  styleUrls: ['./new-room.component.sass']
})
export class NewRoomComponent implements OnInit {

  desks: Desk[] = [];
  room: Room;
  companyId: number;
  lastRoom: string;
  viewBoxTxt: string;
  deskWidth = 100;
  deskHeight = 50;
  id = 0;

  selectedDesk = -1;
  dragging = false;
  dividerX: number;
  dividerY: number;
  color = '#b7ff00';
  meterToPixel = 50;
  maxCapacity = 0;
  valid = false;

  roomForm = this.fb.group({
    number: ['', Validators.required],
    name: ['', [Validators.required, Validators.minLength(4)]],
    width: ['', [Validators.required, Validators.min(5)]],
    height: ['', [Validators.required, Validators.min(5)]],
  });

  capacityForm = this.fb.group({
    capacity: ['', [Validators.required, Validators.min(0), Validators.max(100)]],
  });

  constructor(
    private fb: FormBuilder,
    private roomServ: RoomService,
    private deskService: DeskService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.companyId = +this.route.snapshot.paramMap.get('id');
  }

  onSubmit(stepper: MatStepper) {
    this.room.companyId = this.companyId;
    this.roomServ.postRoom(this.room, this.desks).subscribe(
      data => {
        this.lastRoom = this.room.name;
        this.room = null;
        this.desks = [];
        this.selectedDesk = -1;
        this.maxCapacity = 0;
        stepper.reset();
        this.roomForm.reset();
        this.capacityForm.reset();
      }
    );
  }

  submit() {
    this.lastRoom = null;
    if (this.room != null) {
      this.desks = [];
    }
    this.room = new Room(0, this.roomForm.controls.number.value,
      this.roomForm.controls.name.value,
      this.capacityForm.controls.capacity.value, 0,
      this.roomForm.controls.width.value,
      this.roomForm.controls.height.value);
    this.viewBoxTxt = '0 0 ' + this.room.width * this.meterToPixel + ' ' + this.room.height * this.meterToPixel;

    for (let i = 0; i < this.room.capacity; i++) {
      this.createNewDesk();
    }
    this.countMaxCapacity();
  }

  onKeydown(event) {
    if (event.key === 'q' || event.key === 'Q') {
      this.rotLeft();
    } else if (event.key === 'e' || event.key === 'E') {
      this.rotRight();
    }
  }

  mouseMoveEvent(event) {
    if (this.dragging) {
      const CTM = event.target.getScreenCTM();

      const mouseX = (event.clientX - CTM.e) / CTM.d;
      const fixX = this.desks[this.selectedDesk].width[this.desks[this.selectedDesk].rotation % 2] / this.dividerX;
      const moveToX = mouseX - fixX;

      const mouseY = (event.clientY - CTM.f) / CTM.a;
      const fixY = this.desks[this.selectedDesk].height[this.desks[this.selectedDesk].rotation % 2] / this.dividerY;
      const moveToY = mouseY - fixY;

      // desks width
      let width = this.desks[this.selectedDesk].width[0];
      if (width < this.desks[this.selectedDesk].width[1]) {
        width = this.desks[this.selectedDesk].width[1];
      }
      // desks height/length
      const height = width;

      // poruszanie biurkiem po osi X oraz sprawdzenie czy nie wychodzi poza obszar
      if ((this.desks[this.selectedDesk].positionX - 5 > 0 || this.desks[this.selectedDesk].positionX < moveToX)
        && (this.desks[this.selectedDesk].positionX + width + 5 < this.room.width
          * this.meterToPixel || this.desks[this.selectedDesk].positionX > moveToX)
      ) {
        this.desks[this.selectedDesk].positionX = moveToX;
      }

      // poruszanie biurkiem po osi Y oraz sprawdzenie czy nie wychodzi poza obszar
      if ((this.desks[this.selectedDesk].positionY - 5 > 0 || this.desks[this.selectedDesk].positionY < moveToY)
        && (this.desks[this.selectedDesk].positionY + height + 5 < this.room.height *
          this.meterToPixel || this.desks[this.selectedDesk].positionY > moveToY)
      ) {
        this.desks[this.selectedDesk].positionY = moveToY;
      }
      this.changeColor(event);
    }
  }

  changeColor(event) {
    let width = this.desks[this.selectedDesk].width[0];
    if (width < this.desks[this.selectedDesk].width[1]) {
      width = this.desks[this.selectedDesk].width[1];
    }
    tmp:
    for (let j = 0; j < this.desks.length; j++) {
      for (let i = 0; i < this.desks.length; i++) {
        if (i !== j && Math.abs(this.desks[j].positionX - this.desks[i].positionX)
          <= width
          && Math.abs(this.desks[j].positionY - this.desks[i].positionY)
          <= width) {
          this.desks[j].color = 'red';
          continue tmp;
        }
      }
      this.desks[j].color = '#ad7d1c';
    }
  }

  findDesk(id: number) {
    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.desks.length; i++) {
      // tslint:disable-next-line: triple-equals
      if (this.desks[i].id == id) {
        this.selectedDesk = i;
        return;
      }
    }
    this.selectedDesk = -1;
  }

  // Sprawdzenie czy kliknięto na biurko
  mouseDownEvent(event) {
    const CTM = event.target.getScreenCTM();
    if (!this.dragging) {
      this.findDesk(event.target.id);
      if (this.selectedDesk > -1) {
        this.dragging = true;
        // tslint:disable-next-line: max-line-length
        this.dividerX = this.desks[this.selectedDesk].width[this.desks[this.selectedDesk].rotation % 2] / ((event.clientX - CTM.e) / CTM.d - this.desks[this.selectedDesk].positionX);
        // tslint:disable-next-line: max-line-length
        this.dividerY = this.desks[this.selectedDesk].height[this.desks[this.selectedDesk].rotation % 2] / ((event.clientY - CTM.f) / CTM.a - this.desks[this.selectedDesk].positionY);
      }
    }
  }

  mouseUpEvent(event) {
    // upuszczenie biurka
    if (this.dragging) {
      this.dragging = false;
      this.changeColor(event);
      this.dividerX = -1;
      this.dividerY = -1;
      // desks width
      const width = this.desks[this.selectedDesk].width[this.desks[this.selectedDesk].rotation % 2];
      // desks height/length
      const height = this.desks[this.selectedDesk].height[this.desks[this.selectedDesk].rotation % 2];

      if (this.desks[this.selectedDesk].positionX - 5 < 0) {
        this.desks[this.selectedDesk].positionX = 5;
      }

      if (this.desks[this.selectedDesk].positionY - 5 < 0) {
        this.desks[this.selectedDesk].positionY = 5;
      }

      if (this.desks[this.selectedDesk].positionX + width + 5 > this.room.width * this.meterToPixel) {
        this.desks[this.selectedDesk].positionX = this.room.width * this.meterToPixel - width - 5;
      }

      if (this.desks[this.selectedDesk].positionY + height + 5 > this.room.height * this.meterToPixel) {
        this.desks[this.selectedDesk].positionY = this.room.height * this.meterToPixel - height - 5;
      }

      // tslint:disable-next-line: max-line-length
      this.desks[this.selectedDesk].middle[0] = this.desks[this.selectedDesk].positionX + this.desks[this.selectedDesk].width[this.desks[this.selectedDesk].rotation % 2] / 2;

      // tslint:disable-next-line: max-line-length
      this.desks[this.selectedDesk].middle[1] = this.desks[this.selectedDesk].positionY + this.desks[this.selectedDesk].height[this.desks[this.selectedDesk].rotation % 2] / 2;
    }

    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.desks.length; i++) {
      if (this.desks[i].color === 'red') {
        this.valid = false;
        return;
      }
    }
    this.valid = true;
  }

  // stworzenie nowego biurka
  createNewDesk() {
    const desk = new Desk(this.deskWidth, this.deskHeight, this.id, 100, 100, 0);
    this.id++;
    this.desks.push(desk);
    this.room.desksID.push(desk.id);
  }

  // rotacja w lewo
  rotLeft() {
    // tslint:disable-next-line: curly
    if (this.selectedDesk > -1) {
      this.desks[this.selectedDesk].rotation -= 1;
      if (this.desks[this.selectedDesk].rotation === -1) {
        this.desks[this.selectedDesk].rotation = 3;
      }
    }
  }

  // rotacja w prawo
  rotRight() {
    // tslint:disable-next-line: curly
    if (this.selectedDesk > -1) {
      this.desks[this.selectedDesk].rotation += 1;

      if (this.desks[this.selectedDesk].rotation === 4) {
        this.desks[this.selectedDesk].rotation = 0;
      }
    }
  }

  countMaxCapacity() {

    const width = this.roomForm.controls.width.value * this.meterToPixel;
    const height = this.roomForm.controls.height.value * this.meterToPixel;

    const desksX = Math.floor(width / this.deskWidth - 1);
    const desksY = Math.floor(height / (this.deskHeight * 2) - 1);

    this.maxCapacity = desksX * desksY;

    this.capacityForm.get('capacity').setValidators([Validators.max(this.maxCapacity), Validators.required, Validators.min(0)]);
  }

  back(){
    this.capacityForm.reset();
  }
}
