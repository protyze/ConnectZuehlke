import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Employee} from '../domain/Employee';
import {switchMap} from 'rxjs/operators';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {EmployeeService} from '../services/employee.service';

@Component({
  selector: 'app-employee-detail',
  templateUrl: './employee-detail.component.html',
  styleUrls: ['./employee-detail.component.scss']
})
export class EmployeeDetailComponent implements OnInit {
  employee$: Observable<Employee>;
  code: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: EmployeeService,
  ) {
      this.code = this.route.snapshot.paramMap.get("code");
  }

  ngOnInit() {
    this.employee$ = this.route.paramMap.pipe(
      switchMap((params: ParamMap) => this.service.getEmployee(params.get('code')))
    );
  }

}
