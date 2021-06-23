export class Desk {

    width: number[] = [0, 0];
    height: number[] = [0, 0];
    id: number;
    taken = false;
    color = 'red';
    positionX: number;
    positionY: number;
    rotation: number;
    middle: number[] = [];

    constructor(width, height, id, positionX, positionY, rotation) {
        this.width[0] = width;
        this.height[0] = height;
        this.width[1] = height;
        this.height[1] = width;
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.rotation = rotation;

        this.middle.push(positionX + width / 2);
        this.middle.push(positionY + height / 2);
    }
}
