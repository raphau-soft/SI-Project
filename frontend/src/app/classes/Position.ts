export class Position {
    id: number;
    name: string;
    minWage: number;
    maxWage: number;
    buildingId: number;
    usage = 0;
    all: string[];

    constructor(id?, name?, minWage?, usage?, maxWage?, inTable?) {
        this.name = name;
        this.minWage = minWage;
        this.maxWage = maxWage;
        this.id = id;
        this.usage = usage;
        this.all = [];
        this.all.push(inTable);
        this.all.push(name);
        this.all.push(minWage);
        if(maxWage == 0)
            this.all.push('-');
        else 
            this.all.push(maxWage);
        this.all.push(usage);
    }
}
