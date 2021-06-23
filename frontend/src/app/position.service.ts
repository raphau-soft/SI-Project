import { Injectable } from '@angular/core';
import { Position } from './classes/Position';

@Injectable({
  providedIn: 'root'
})
export class PositionService {

  constructor() { }

  getPositions(): Position[] {
    const localStorageItem = JSON.parse(localStorage.getItem('positions'));
    return localStorageItem == null ? [] : localStorageItem.positions;
  }

  addPosition(position: Position) {
    const positions = this.getPositions();
    positions.push(position);

    localStorage.setItem('positions', JSON.stringify({positions}));
  }

  getId(): number {
    const positions = this.getPositions();
    const maxId = positions.length === 0 ? 0 : positions[positions.length - 1].id;
    return maxId + 1;
  }

  getPosition(id: number): string {
    let positions = this.getPositions();
    positions = positions.filter(position => position.id === id);
    return positions[0].name;
  }
  getPositionO(id: number): Position {
    let positions = this.getPositions();
    positions = positions.filter(position => position.id === id);
    console.log(positions[0].name)
    return positions[0];
  }

  incrementPopulation(id: number) {
    const position = this.getPositionO(id);
    position.usage += 1;
    this.updatePosition(position);
  }

  updatePosition(position: Position) {
    this.remPosition(position.id);
    this.addPosition(position);
  }

  remPosition(id: number) {
    let position = this.getPositions();
    // tslint:disable-next-line: no-shadowed-variable
    position = position.filter(position => position.id !== id);
    this.sendToDatabase(position);
  }

  decrementPopulation(id: number) {
    const position = this.getPositionO(id);
    position.usage -= 1;
    this.updatePosition(position);
  }

  sendToDatabase(positions: Position[]) {
    positions = positions.sort((n1, n2) => {
      if (n1.id > n2.id) {
        return 1;
      }
      if (n1.id < n2.id) {
        return -1;
      }
      return 0;
    });

    localStorage.setItem('positions', JSON.stringify({positions}));
  }
}
