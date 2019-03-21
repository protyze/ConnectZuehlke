import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-employee-image',
  templateUrl: './employee-image.component.html',
  styleUrls: ['./employee-image.component.scss']
})
export class EmployeeImageComponent implements OnInit {
  @Input() employeeId: number;
  @Input() size = 100;

  constructor() { }

  ngOnInit() {
  }

}
