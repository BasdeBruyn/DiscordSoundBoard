import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';
import {Injectable} from '@angular/core';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService) {}

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot):
    Observable<boolean> | Promise<boolean> | boolean {
    if (this.authService.isAuthorized) {
      return true;
    } else {
      this.authService.login();
      return false;
    }
  }
}
