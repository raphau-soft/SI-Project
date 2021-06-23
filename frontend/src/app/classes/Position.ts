export class Position {
    id: number;
    name: string;
    minWage: number;
    maxWage: number;
    usage = 0;

    constructor(id?, name?, minWage?, usage?, maxWage?) {
        this.name = name;
        this.minWage = minWage;
        this.maxWage = maxWage;
        this.id = id;
        this.usage = usage;
    }
}
