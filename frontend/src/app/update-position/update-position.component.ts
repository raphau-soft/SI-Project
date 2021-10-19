import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Position } from '../classes/Position';
import { PositionService } from '../position.service';

@Component({
  selector: 'app-update-position',
  templateUrl: './update-position.component.html',
  styleUrls: ['./update-position.component.sass']
})
export class UpdatePositionComponent implements OnInit {

  id: number;
  lastPosition: string;
  position: Position;
  positionForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(4)]],
    minWage: ['', [Validators.required, Validators.min(1600)]],
    maxWage: [''],
  });


  constructor(private route: ActivatedRoute, private positionService: PositionService,
    private fb: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.id = +this.route.snapshot.paramMap.get('id');
    this.positionService.getPosition(this.id).subscribe(
      data => {
        this.position = JSON.parse(data);
        this.positionForm.controls.name.setValue(this.position.name);
        this.positionForm.controls.maxWage.setValue(this.position.maxWage);
        this.positionForm.controls.minWage.setValue(this.position.minWage);
      },
      error => {
        console.log(error);
      }
    )
  }

  onSubmit() {
    let maxWage = this.positionForm.controls.maxWage.value;
    if (maxWage === '' || maxWage < this.positionForm.controls.minWage.value) { maxWage = null; }
    let positionO = new Position(this.position.id, this.positionForm.controls.name.value,
      this.positionForm.controls.minWage.value, this.position.usage, this.positionForm.controls.maxWage.value);
    positionO.buildingId = this.position.buildingId;
    this.positionService.putPosition(positionO).subscribe(
      data => {
        this.lastPosition = positionO.name;
        setTimeout(() => this.router.navigateByUrl('/in-building/' + this.id), 1500)
      }
    );
  }

}
