import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InBuildingComponent } from './in-building.component';

describe('InBuildingComponent', () => {
  let component: InBuildingComponent;
  let fixture: ComponentFixture<InBuildingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InBuildingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InBuildingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
