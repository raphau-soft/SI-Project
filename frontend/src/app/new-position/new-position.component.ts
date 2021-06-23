import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Position } from '../classes/Position';
import { PositionService } from '../position.service';

@Component({
  selector: 'app-new-position',
  templateUrl: './new-position.component.html',
  styleUrls: ['./new-position.component.sass']
})
export class NewPositionComponent implements OnInit {

  lastPosition: string;

  constructor(
    private fb: FormBuilder,
    private posServ: PositionService,
  ) { }

  positionForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(4)]],
    minWage: ['', [Validators.required, Validators.min(1600)]],
    maxWage: [''],
  });

  ngOnInit() {
  }

  getId(): number {
    return this.posServ.getId();
  }

  onMinChange() {
    this.positionForm.get('maxWage').setValidators([Validators.min(this.positionForm.controls.minWage.value)]);
  }

  onSubmit() {
    let maxWage = this.positionForm.controls.maxWage.value;
    if (maxWage === '' || maxWage < this.positionForm.controls.minWage.value) { maxWage = null; }
    console.log(maxWage);
    const position = new Position(this.getId(), this.positionForm.controls.name.value,
      this.positionForm.controls.minWage.value, 0, maxWage);
    this.positionForm.reset();
    this.posServ.addPosition(position);
    this.lastPosition = position.name;
  }
}
