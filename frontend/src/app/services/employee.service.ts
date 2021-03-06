import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { HttpClient, HttpParams } from '@angular/common/http';
import { Employee } from '../domain/Employee';
import { Team } from '../domain/Team';
import { Filter } from '../domain/Filter';
import { RelationshipData } from "../domain/wheel-chart";

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private teams: Array<Team> = [{
    score: {
      value: 1
    },
    teamMembers: [
      { employee: { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo' }, available: false },
      { employee: { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo' }, available: false }
    ]
  }, {
    score: {
      value: 1
    },
    teamMembers: [
      { employee: { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo' }, available: false },
      { employee: { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo' }, available: false }
    ]
  }];

  private loading = false;

  teamsFetched = new Subject<void>();
  loadingChanged = new Subject<void>();


  constructor(private http: HttpClient) {

  }

  public fetchTeams(filters: Filter) {
    this.startLoading();
    this.http
      .get('/api/ateams', {
        params: new HttpParams()
          .set('nrOfTeamMembers', filters.numberOfEmployees.toString())
          .set('locations', filters.locations.map(location => location.cityName).join())
      })
      .subscribe((data: Team[]) => {
        this.teams = data.map((team, idx) => ({ ...team, score: { value: parseFloat(((20 - idx) - Math.random()).toFixed(2)) } }));
        this.teamsFetched.next();
      }, this.finishedLoading.bind(this), this.finishedLoading.bind(this));
  }

  public getTeams() {
    return this.teams;
  }

  public isLoading() {
    return this.loading;
  }

  public getAllEmployees(): Observable<Employee[]> {

    return this.http
      .get<Employee[]>('/api/employees')
      .pipe(catchError(this.handleError('getAllEmployees', [])));

  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  private log(s: string) {
    console.log(`${this}: ${s}`);
  }

  getEmployee(id: string): Observable<Employee> {
    return this.http
      .get<Employee>(`/api/employee/${id}`)
      .pipe(catchError(this.handleError('getEmployee', null)));
  }

  getRelationshipData(members: Employee[]): Observable<RelationshipData> {
    return this.http
      .get<RelationshipData>(`/api/employee/relations`, {
        params: new HttpParams()
          .set('employees', members.map(emp => (emp.employee.code)).join())
      })
      .pipe(catchError(this.handleError('getRelationshipData', null)));
  }

  startLoading() {
    this.setLoading(true);
  }

  finishedLoading() {
    this.setLoading(false);
  }

  setLoading(loading) {
    this.loading = loading;
    this.loadingChanged.next();
  }
}
