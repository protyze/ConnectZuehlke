import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeImageComponent } from './employee-image.component';

describe('EmployeeImageComponent', () => {
  let component: EmployeeImageComponent;
  let fixture: ComponentFixture<EmployeeImageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmployeeImageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
