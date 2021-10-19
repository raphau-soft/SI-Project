import { Injectable } from '@angular/core';
import { Position } from './classes/Position';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const API = "http://localhost:8080/position";
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class PositionService {

  constructor(
    private http: HttpClient
  ) { }

  getPositionsByBuildingId(id): Observable<any> {
    return this.http.get(API + "/building/" + id, { responseType: 'text' });
  }

  postPosition(position: Position): Observable<any> {
    return this.http.post(API, position, httpOptions)
  }

  putPosition(position: Position): Observable<any> {
    return this.http.put(API, position, httpOptions);
  }

  getPosition(id: number){
    return this.http.get(API + "/" + id, { responseType: 'text'});
  }

  deletePositionById(id): Observable<any>{
    return this.http.delete(API + "/" + id);
  }

  getId(): number {
    // const positions = this.getPositions();
    // const maxId = positions.length === 0 ? 0 : positions[positions.length - 1].id;
    // return maxId + 1;
    return 0;
  }

  getPositionO(id: number): Position {
    // let positions = this.getPositions();
    // positions = positions.filter(position => position.id === id);
    // console.log(positions[0].name)
    return new Position();
  }

  incrementPopulation(id: number) {
    const position = this.getPositionO(id);
    position.usage += 1;
    this.updatePosition(position);
  }

  updatePosition(position: Position) {
    this.remPosition(position.id);
    //this.addPosition(position);
  }

  remPosition(id: number) {
    //let position = this.getPositions();
    // tslint:disable-next-line: no-shadowed-variable
    //position = position.filter(position => position.id !== id);
    //this.sendToDatabase(position);
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
