export class Room {
    id: number;
    number: number;
    name: string;
    capacity = 0;
    population = 0;
    desksID: number[] = [];
    // TODO doda długości i szerokości
    width: number;
    height: number;

    constructor(id?, num?, name?, capacity?, population?, width?, height?) {
        this.width = width;
        this.height = height;
        this.number = num;
        this.name = name;
        this.capacity = capacity;
        this.population = population;
        this.id = id;
    }

}
