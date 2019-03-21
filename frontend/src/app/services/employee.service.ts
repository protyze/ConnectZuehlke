import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Employee } from '../domain/Employee';
import { Team } from '../domain/Team';
import { Filter } from '../domain/Filter';

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private teams: Array<Team> = [{
    employees: [
      { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo', isAvailable: false, role: 'test', unit: 'test', skills: ['a'], groups: ['a'] },
      { firstName: 'Max', lastName: 'Mustermann', id: 1, code: 'jdo', isAvailable: false, role: 'test', unit: 'test', skills: ['a'], groups: ['a'] }
    ]
  }, {
    employees: [
      { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo', isAvailable: false, role: 'test', unit: 'test', skills: ['a'], groups: ['a'] },
      { firstName: 'Max', lastName: 'Mustermann', id: 1, code: 'jdo', isAvailable: false, role: 'test', unit: 'test', skills: ['a'], groups: ['a'] }
    ]
  }];

  teamsFetched = new Subject<void>();

  constructor(private http: HttpClient) {
  }

  public fetchTeams(filters: Filter) {
    // API Request to get Teams
    // Notify Subscribers about updated Teams!
    console.error('Not implemented');
    this.teamsFetched.next();
  }

  public getTeams() {
    return this.teams;
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
}
