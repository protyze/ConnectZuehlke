import { Component, OnInit, Input } from '@angular/core';
import { Employee } from '../domain/Employee';

@Component({
  selector: 'app-team-member',
  templateUrl: './team-member.component.html',
  styleUrls: ['./team-member.component.scss']
})
export class TeamMemberComponent implements OnInit {
  @Input() employee: Employee;

  constructor() { }

  ngOnInit() {
  }

}
