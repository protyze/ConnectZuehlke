import { Employee } from './Employee';

interface Score {
  value: number;
}

export class Team {
  score: Score;
  teamMembers: Array<Employee> = [];
}
