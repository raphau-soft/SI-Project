import { Building } from './Building';
import { Desk } from './Desk';
import { Position } from './Position';
import { Room } from './Room';

export class Employee {
    id: number;
    firstName: string;
    lastName: string;
    position: Position;
    room: Room;
    building: Building;
    salary: number;
    desk: Desk;
    all: string[];
    buildingId: number;
    positionId: number;
    roomId: number;
    deskId: number;

    constructor(id, firstName, lastName, salary?, position?, room?, company?, inTable?) {
        this.all = [];
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.id = id;
        this.all.push(inTable);
        this.all.push(firstName);
        this.all.push(lastName);
        if(position == null){
            this.all.push("-");
        } else {
            this.all.push(position.name);
        }
        if(room == null){
            this.all.push("-");
        } else {
            this.all.push(room.number);
        }
        this.all.push(salary);
    }
}
