import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ATeamAudioComponent } from './a-team-audio.component';

describe('ATeamAudioComponent', () => {
  let component: ATeamAudioComponent;
  let fixture: ComponentFixture<ATeamAudioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ATeamAudioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ATeamAudioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
