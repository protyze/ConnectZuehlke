import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { HttpClient, HttpParams } from '@angular/common/http';
import { Employee } from '../domain/Employee';
import { Team } from '../domain/Team';
import { Filter } from '../domain/Filter';

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private teams: Array<Team> = [{
    score: {
      value: 1
    },
    teamMembers: [
      { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo', available: false },
      { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo', available: false }
    ]
  }, {
    score: {
      value: 1
    },
    teamMembers: [
      { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo', available: false },
      { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo', available: false }
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
          .set('nE', filters.numberOfEmployees.toString())
          .set('skills', filters.skills.map(skill => skill.name).join())
      })
      .subscribe((data: Team[]) => {
        this.teams = data;
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
