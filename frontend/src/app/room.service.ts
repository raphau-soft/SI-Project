import { Injectable } from '@angular/core';
import { Room } from './classes/Room';
import { Desk } from './classes/Desk';
import { DeskService } from './desk.service';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(
    private deskService: DeskService,
  ) { }

  addRoom(room: Room) {
    const rooms = this.getRooms();
    rooms.push(room);
    this.sendRoomsToDatabase(rooms);
  }

  remRoom(id: number) {
    let rooms = this.getRooms();
    rooms = rooms.filter(room => room.id !== id);
    this.sendRoomsToDatabase(rooms);
  }

  getRooms(): Room[] {
    const localStorageItem = JSON.parse(localStorage.getItem('rooms'));
    return localStorageItem === null ? [] : localStorageItem.rooms;
  }

  getId(): number {
    const rooms = this.getRooms();
    const maxId = rooms.length === 0 ? 0 : rooms[rooms.length - 1].id;
    return maxId + 1;
  }

  incrementPopulation(id: number) {
    const room = this.getRoomO(id);
    room.population += 1;
    this.updateRoom(room);
  }

  updateRoom(room: Room) {
    this.remRoom(room.id);
    this.addRoom(room);
  }

  decrementPopulation(id: number) {
    const room = this.getRoomO(id);
    room.population -= 1;
    this.updateRoom(room);
  }

  getCapacity(id: number): number {
    return this.getRoomO(id).capacity;
  }

  getPopulation(id: number): number {
    return this.getRoomO(id).population;
  }

  getRoomO(id: number): Room {
    const rooms = this.getRooms();
    const roomsFilt = rooms.filter(room => room.id === id);
    return roomsFilt[0];
  }

  getRoom(id: number): string {
    const rooms = this.getRooms();
    const roomsFilt = rooms.filter(room => room.id === id);
    return roomsFilt[0] == null ? '' : roomsFilt[0].number + ' - ' + roomsFilt[0].name;
  }

  sendRoomsToDatabase(rooms: Room[]) {
    rooms = rooms.sort((n1, n2) => {
      if (n1.id > n2.id) {
        return 1;
      }
      if (n1.id < n2.id) {
        return -1;
      }
      return 0;
    });
    localStorage.setItem('rooms', JSON.stringify({ rooms }));
  }
}
