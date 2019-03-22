class jobProfile {
  name: string;
}

class EmployeeProfile {
  firstName: string;
  lastName: string;
  id: number;
  code: string;
  location?: string;
  jobProfile?: jobProfile;
  title?: string;
}

export interface Employee {
  employee: EmployeeProfile;
  available: boolean;
  job?: string;
  grade?: string;
  unit?: string;
  skills?: Array<string>;
  focusGroups?: Array<string>;
}
