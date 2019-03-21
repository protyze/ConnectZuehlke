import { Observable, of } from 'rxjs';
import { Employee } from '../domain/Employee';

export const EMPLOYEES: Employee[] = [
  { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo', isAvailable: false, role: 'test', unit: 'test', skills: ['a'], groups: ['a'] },
  { firstName: 'Max', lastName: 'Mustermann', id: 1, code: 'jdo', isAvailable: false, role: 'test', unit: 'test', skills: ['a'], groups: ['a'] }
];

export class EmployeeServiceMock {
  getAllEmployees(): Observable<Employee[]> {
    return of(EMPLOYEES);
  }

  getEmployee(code: string): Observable<Employee> {
    return of(EMPLOYEES.find(e => e.code === code));
  }
}
