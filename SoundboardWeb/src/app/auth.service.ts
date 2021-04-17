import {Injectable} from '@angular/core';
import {Subject, Subscriber} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _isAuthorized = false;
  private _isAuthorizedObs = new Subject<boolean>();

  private _discordToken = '';
  private _discordTokenObs = new Subject<string>();

  constructor(private router: Router,
              private httpClient: HttpClient) {
  }

  login(): void {
    const queryParams = new HttpParams({
      fromObject: {
        client_id: '',
        redirect_uri: 'http://localhost:4200/redirect',
        response_type: 'code',
        scope: 'identify guilds'
      }
    });
    window.location.href = 'https://discord.com/api/oauth2/authorize?' + queryParams.toString();
  }

  requestToken(code: string): void {
    const body = new URLSearchParams();
    body.set('client_id', '');
    body.set('client_secret', '');
    body.set('grant_type', 'authorization_code');
    body.set('redirect_uri', 'http://localhost:4200/redirect');
    body.set('scope', 'identify guilds');
    body.set('code', code);

    const options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };

    this.httpClient
      .post<any>('https://discord.com/api/v6/oauth2/token', body.toString(), options)
      .subscribe(response => {
        this._isAuthorized = true;
        this._discordToken = response.access_token;
        this._isAuthorizedObs.next(this._isAuthorized);
        this._discordTokenObs.next(this._discordToken);
        this.router.navigate(['']);
      });
  }

  get isAuthorized(): boolean {
    return this._isAuthorized;
  }

  get discordToken(): string {
    return this._discordToken;
  }

  get discordTokenObs(): Subject<string> {
    return this._discordTokenObs;
  }

  get isAuthorizedObs(): Subject<boolean> {
    return this._isAuthorizedObs;
  }
}
