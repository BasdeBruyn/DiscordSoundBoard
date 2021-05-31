import {Injectable} from '@angular/core';
import {AuthService} from '../../auth.service';
import {HttpClient} from '@angular/common/http';
import {SoundEffect} from '../../models/SoundEffect';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SoundboardService {
  constructor(
    private authService: AuthService,
    private httpClient: HttpClient
  ) {
  }

  playSoundEffect(soundEffect: SoundEffect): Promise<any> {
    const headers = {Authorization: 'Bearer ' + this.authService.jwtToken};
    return this.httpClient
      .post(environment.apiBaseUrl + '/soundboard/play/' + soundEffect.name, {}, {headers})
      .toPromise();
  }
}
