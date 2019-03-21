import { Observable, of } from 'rxjs';
import { Employee } from '../domain/Employee';

export const EMPLOYEES: Employee[] = [
  { employee: { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo' }, available: false },
  { employee: { firstName: 'John', lastName: 'Doe', id: 1, code: 'jdo' }, available: false }
];

export class EmployeeServiceMock {
  getAllEmployees(): Observable<Employee[]> {
    return of(EMPLOYEES);
  }

  getEmployee(code: string): Observable<Employee> {
    return of(EMPLOYEES.find(e => e.employee.code === code));
  }
}
