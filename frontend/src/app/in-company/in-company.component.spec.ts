import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InCompanyComponent } from './in-company.component';

describe('InCompanyComponent', () => {
  let component: InCompanyComponent;
  let fixture: ComponentFixture<InCompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InCompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
