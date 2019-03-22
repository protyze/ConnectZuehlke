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

  getSimilarityIndicators() {
    return ['Software Engineer', 'UX Design', 'Zühlke'];
    /*return this.team.teamMembers.map((empl) => (empl ? [
      ...(empl.focusGroups ? empl.focusGroups : []),
      empl.unit
    ] : [])).reduce((acc, val) => acc.concat(val), []);*/
  }

}
