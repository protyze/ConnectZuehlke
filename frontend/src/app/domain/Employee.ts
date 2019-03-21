class jobProfile {
  name: string;
}

export interface Employee {
  firstName: string;
  lastName: string;
  id: number;
  code: string;
  isAvailable: boolean;
  role: string;
  unit: string;
  location: string;
  skills: Array<string>;
  groups: Array<string>;
  jobProfile: jobProfile;
}
