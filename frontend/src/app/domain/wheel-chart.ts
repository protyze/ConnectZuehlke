import {ObjectData} from "gojs";

export class RelationshipData {

  employeeNodes: Array<EmployeeNode>;
  employeeRelations: Array<EmployeeRelationship>;

}

export class EmployeeNode implements ObjectData{

  constructor(key: number, text: string, color: string) {
    this.key = key;
    this.text = text;
    this.color = color;
  }

  key: number;
  text: string;
  color: string;

  [index: string]: any;
}

export class EmployeeRelationship implements ObjectData {

  constructor(from: number, to: number, color: string) {
    this.from = from;
    this.to = to;
    this.color = color;
  }

  from: number;
  to: number;
  color: string;

  [index: string]: any;
}
