import { Position } from './Position';
import { Room } from './Room';

export class Employee {
    id: number;
    firstName: string;
    lastName: string;
    positionId: number;
    roomId: number;
    salary: number;
    deskId: number;

    constructor(id, firstName, lastName, position, salary, room?) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.positionId = position;
        this.salary = salary;
        this.roomId = room;
        this.id = id;
    }
}
