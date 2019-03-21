import { Component, OnInit, Input } from '@angular/core';

import { Team } from '../domain/Team';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.scss']
})
export class TeamComponent implements OnInit {
  @Input() team: Team;

  constructor() { }

  ngOnInit() {
  }

}
