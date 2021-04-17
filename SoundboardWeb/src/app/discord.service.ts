import {Injectable} from '@angular/core';
import {AuthService} from './auth.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Guild} from './models/guild';

@Injectable({
  providedIn: 'root'
})
export class DiscordService {

  constructor(private authService: AuthService, private httpClient: HttpClient ) {
  }

  getGuilds(): Observable<Guild[]> {
    const headers = {Authorization: 'Bearer ' + this.authService.discordToken};
    return this.httpClient.get<Guild[]>('https://discordapp.com/api/users/@me/guilds', {headers});
  }
}
