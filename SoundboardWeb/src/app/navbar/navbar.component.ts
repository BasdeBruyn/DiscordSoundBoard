import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  public isAuthenticated = false;
  public isAuthenticatedObs = new Subscription();

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.isAuthenticated = this.authService.isAuthorized;
    this.isAuthenticatedObs = this.authService.isAuthorizedObs.subscribe(isAuthenticated => this.isAuthenticated = isAuthenticated);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['']);
  }

  login(): void {
    this.authService.login();
  }
}
