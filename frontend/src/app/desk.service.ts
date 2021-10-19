import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Desk } from './classes/Desk';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const API = "http://localhost:8080/desk";
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class DeskService {

  constructor(private http: HttpClient) { }

  getDesksByRoomId(id):Observable<any>{
    return this.http.get(API + "/room/" + id, {responseType: 'text'});
  }

  getSpecifiedDesks(desksID: number[]): Desk[] {
    const actualDesks = this.getDesks();
    const specifiedDesks = [];
    // tslint:disable-next-line: prefer-for-of
    for (let j = 0; j < actualDesks.length; j++) {
      let x = 0;
      // tslint:disable-next-line: prefer-for-of
      for (let i = 0; i < desksID.length; i++) {
        if (actualDesks[j].id === desksID[i]) {
          x = 1;
          break;
        }
      }
      if (x === 1) {
        specifiedDesks.push(actualDesks[j]);
      }
    }
    return specifiedDesks;
  }

  sendDesksToDatabase(desks: Desk[]) {
    desks = desks.sort((n1, n2) => {
      if (n1.id > n2.id) {
        return 1;
      }
      if (n1.id < n2.id) {
        return -1;
      }
      return 0;
    });
    localStorage.setItem('desks', JSON.stringify({ desks }));
  }

  getDesks(): Desk[] {
    const localStorageItem = JSON.parse(localStorage.getItem('desks'));
    return localStorageItem === null ? [] : localStorageItem.desks;
  }

  getDeskId(): number {
    const desks = this.getDesks();
    const maxId = desks.length === 0 ? 0 : desks[desks.length - 1].id;
    return maxId + 1;
  }

  addDesk(desk: Desk) {
    const desks = this.getDesks();
    desks.push(desk);
    this.sendDesksToDatabase(desks);
  }

  deleteDesks(desks: Desk[]) {
    const baseDesks = this.getDesks();
    const newDesks = baseDesks.filter(desk => desks.indexOf(desk) < 0);
    this.sendDesksToDatabase(newDesks);
  }

  getDesk(id: number) {
    const desks = this.getDesks();
    let i = 0;
    while (i < desks.length) {
      if (id === desks[i].id) {
        return desks[i];
      }
      i++;
    }
  }

  freeDesk(id: number) {
    const desk = this.getDesk(id);
    console.log(id + ' - ' + ' free');
    desk.taken = false;
    desk.color = '#ad7d1c';
    this.updateDesk(desk);
  }

  updateDesk(desk: Desk) {
    const actualDesks = this.getDesks();
    let i = 0;
    while (i < actualDesks.length) {
      if (desk.id === actualDesks[i].id) {
        actualDesks[i] = desk;
      }
      i++;
    }
    this.sendDesksToDatabase(actualDesks);
  }

  updateDesks(desks: Desk[]) {
    const actualDesks = this.getDesks();
    const updatedDesks = [];

    // tslint:disable-next-line: prefer-for-of
    for (let j = 0; j < actualDesks.length; j++) {
      let x = 0;
      // tslint:disable-next-line: prefer-for-of
      for (let i = 0; i < desks.length; i++) {
        if (actualDesks[j].id === desks[i].id) {
          x = 1;
          break;
        }
      }
      if (x === 0) {
        updatedDesks.push(actualDesks[j]);
      }
    }
    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < desks.length; i++) {
      updatedDesks.push(desks[i]);
    }

    this.sendDesksToDatabase(updatedDesks);
  }
}
