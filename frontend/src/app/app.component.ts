import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Circle, Line, Stroke, Polygon, Vertex, Polyline } from 'angular-svg';
import { TokenStorageService } from './_services/token-storage.service';
import { Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass'],
})
export class AppComponent {

  isLoggedIn = false;
  roles: string[] = [];

  ngOnInit(): void {
    if(this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  constructor(private tokenStorage: TokenStorageService, private router: Router) {
  }
  title = 'EmployeeManager';

  openNav() {
    document.getElementById('mySidenav').style.width = '250px';
    document.getElementById('main').style.marginLeft = '250px';
  }

  closeNav() {
    document.getElementById('mySidenav').style.width = '0px';
    document.getElementById('main').style.marginLeft = '0px';
  }

  logout() {
    this.tokenStorage.signOut();
    this.router.navigateByUrl('/signin').then(() => {
      window.location.reload();
    })
  }

}