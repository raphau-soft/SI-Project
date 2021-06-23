export class Data {

    all: string[];
    id: number;
    firstName: string;
    lastName: string;
    positionName: string;
    roomName: string;
    salary: number;

    constructor(id, firstName, lastName, position, salary, room?) {
        var temp = [];
        temp.push(this.id = id);
        temp.push(this.firstName = firstName);
        temp.push(this.lastName = lastName);
        temp.push(this.positionName = position);
        temp.push(this.salary = salary);
        temp.push(this.roomName = room);
        this.all = temp;
    }
}