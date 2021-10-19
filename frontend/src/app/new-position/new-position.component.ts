import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Position } from '../classes/Position';
import { PositionService } from '../position.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-new-position',
  templateUrl: './new-position.component.html',
  styleUrls: ['./new-position.component.sass']
})
export class NewPositionComponent implements OnInit {

  lastPosition: string;
  buildingId: number;

  constructor(
    private fb: FormBuilder,
    private posServ: PositionService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  positionForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(4)]],
    minWage: ['', [Validators.required, Validators.min(1600)]],
    maxWage: [''],
  });

  ngOnInit() {
    this.buildingId = +this.route.snapshot.paramMap.get('id');
  }

  onMinChange() {
    this.positionForm.get('maxWage').setValidators([Validators.min(this.positionForm.controls.minWage.value)]);
  }

  onSubmit() {
    let maxWage = this.positionForm.controls.maxWage.value;
    if (maxWage === '' || maxWage < this.positionForm.controls.minWage.value) { maxWage = null; }
    let position = new Position(0, this.positionForm.controls.name.value,
      this.positionForm.controls.minWage.value, 0, maxWage);
    this.positionForm.reset();
    position.buildingId = this.buildingId;
    this.posServ.postPosition(position).subscribe(
      data => {
        this.lastPosition = position.name;
      }
    );
  }
}
