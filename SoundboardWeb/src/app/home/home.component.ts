import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  public isAuthorized = false;
  private isAuthorizedObs = new Subscription();

  constructor(public authService: AuthService) {
  }

  ngOnInit(): void {
    this.authService.tryLogin();

    this.isAuthorized = this.authService.isAuthorized;
    this.isAuthorizedObs = this.authService.isAuthorizedObs.subscribe(isAuthorized => this.isAuthorized = isAuthorized);
  }

  ngOnDestroy(): void {
    this.isAuthorizedObs.unsubscribe();
  }
}
