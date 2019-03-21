class jobProfile {
  name: string;
}

export interface Employee {
  firstName: string;
  lastName: string;
  id: number;
  code: string;
  available: boolean;
  job?: string;
  grade?: string;
  unit?: string;
  location?: string;
  skills?: Array<string>;
  focusGroups?: Array<string>;
  jobProfile?: jobProfile;
}
