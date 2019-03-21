import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelationWheelComponent } from './relation-wheel.component';

describe('RelationWheelComponent', () => {
  let component: RelationWheelComponent;
  let fixture: ComponentFixture<RelationWheelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelationWheelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelationWheelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
