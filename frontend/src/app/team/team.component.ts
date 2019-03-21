import { Component, OnInit, Input } from '@angular/core';

import { Team } from '../domain/Team';

enum Display { Team, Chart }

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.scss']
})
export class TeamComponent implements OnInit {
  @Input() team: Team;

  availableDisplays = Display;

  display: Display = Display.Team;

  constructor() { }

  ngOnInit() {
  }

  onDisplayChange(display: Display) {
    this.display = display;
  }

}
