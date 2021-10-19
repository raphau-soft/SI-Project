import { Company } from "./Company";

export class Room {
    id: number;
    number: number;
    name: string;
    capacity = 0;
    population = 0;
    desksID: number[] = [];
    company: Company;
    companyId: number;
    // TODO doda długości i szerokości
    width: number;
    height: number;
    all: string[];

    constructor(id?, num?, name?, capacity?, population?, width?, height?) {
        this.width = width;
        this.height = height;
        this.number = num;
        this.name = name;
        this.capacity = capacity;
        this.population = population;
        this.id = id;
        this.all = [];
        this.all.push(num);
        this.all.push(name);
        this.all.push(capacity);
        this.all.push(population);
        this.all.push(width);
        this.all.push(height);
    }

}
