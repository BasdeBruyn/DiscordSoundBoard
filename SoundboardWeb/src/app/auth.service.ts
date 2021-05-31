import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {environment} from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _isAuthorized = false;
  private _isAuthorizedObs = new Subject<boolean>();

  private _jwtToken = '';
  private _jwtTokenObs = new Subject<string>();

  constructor(
    private router: Router,
    private httpClient: HttpClient
  ) {
  }

  tryLogin(): boolean {
    const storedToken = localStorage.getItem('jwtToken');
    if (!storedToken) {
      return false;
    }

    this._isAuthorized = true;
    this._jwtToken = storedToken;
    this._isAuthorizedObs.next(this._isAuthorized);
    this._jwtTokenObs.next(this._jwtToken);
    this.router.navigate(['soundboard']);
    return true;
  }

  login(): void {
    if (!this.tryLogin()) {
      const queryParams = new HttpParams({
        fromObject: {
          client_id: environment.discordClientId,
          redirect_uri: environment.discordRedirectUri,
          response_type: 'code',
          scope: 'identify guilds'
        }
      });
      window.location.href = environment.discordAuthorizeUrl + queryParams.toString();
    }
  }

  logout(): void {
    localStorage.removeItem('jwtToken');
    this._isAuthorized = false;
    this._jwtToken = '';
    this._isAuthorizedObs.next(this._isAuthorized);
    this._jwtTokenObs.next(this._jwtToken);
  }

  requestToken(code: string): Promise<void> {
    const request = this.httpClient.get(environment.apiBaseUrl + '/auth/token?code=' + code, {responseType: 'text'});
    return request.toPromise()
      .then((response: string) => {
        this._isAuthorized = true;
        this._jwtToken = response;
        this._isAuthorizedObs.next(this._isAuthorized);
        this._jwtTokenObs.next(this._jwtToken);
        localStorage.setItem('jwtToken', this._jwtToken);
      });
  }

  get isAuthorized(): boolean {
    return this._isAuthorized;
  }

  get jwtToken(): string {
    return this._jwtToken;
  }

  get jwtTokenObs(): Subject<string> {
    return this._jwtTokenObs;
  }

  get isAuthorizedObs(): Subject<boolean> {
    return this._isAuthorizedObs;
  }
}
