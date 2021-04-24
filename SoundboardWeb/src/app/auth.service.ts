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

  login(): void {
    const storedToken = localStorage.getItem('jwtToken');
    if (storedToken) {
      this._isAuthorized = true;
      this._jwtToken = storedToken;
      this._isAuthorizedObs.next(this._isAuthorized);
      this._jwtTokenObs.next(this._jwtToken);
      this.router.navigate(['musicplayer']);
    } else {
      const queryParams = new HttpParams({
        fromObject: {
          client_id: environment.discordClientId,
          redirect_uri: environment.discordRedirectUri,
          response_type: 'code',
          scope: 'identify guilds'
        }
      });
      window.location.href = 'https://discord.com/api/oauth2/authorize?' + queryParams.toString();
    }
  }

  requestToken(code: string): Promise<void> {
    const request = this.httpClient.get('http://localhost:9090/api/auth/token?code=' + code, {responseType: 'text'});
    return request.toPromise()
      .then((response: string) => {
        this._isAuthorized = true;
        this._jwtToken = response;
        this._isAuthorizedObs.next(this._isAuthorized);
        this._jwtTokenObs.next(this._jwtToken);
        localStorage.setItem('jwtToken', this._jwtToken);
      })
      .catch(console.log);
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
