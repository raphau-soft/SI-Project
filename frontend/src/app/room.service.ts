import { Injectable } from '@angular/core';
import { DeskService } from './desk.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const API = "http://localhost:8080/room";
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(
    private http: HttpClient
  ) { }

  getRoomsByBuildingId(id):Observable<any>{
    return this.http.get(API + "/building/" + id, { responseType: 'text' });
  }

  deleteRoomById(id):Observable<any>{
    return this.http.delete(API + "/" + id);
  }

  postRoom(room, desks):Observable<any>{
    let request = {
      desks,
      room
    }
    return this.http.post(API, request, httpOptions);
  }
}
