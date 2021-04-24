import {Injectable} from '@angular/core';
import {AuthService} from './auth.service';
import {HttpClient} from '@angular/common/http';
import {Subject} from 'rxjs';
import {SoundEffect} from './models/SoundEffect';
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SoundEffectService {
  private _soundEffects: SoundEffect[] = [];
  private _soundEffectsObs = new Subject<SoundEffect[]>();

  constructor(
    private authService: AuthService,
    private httpClient: HttpClient
  ) {
  }

  retrieveSoundEffects(): void {
    const headers = {Authorization: 'Bearer ' + this.authService.jwtToken};
    this.httpClient.get<SoundEffect[]>(environment.apiBaseUrl + '/api/sound_effect', {headers})
      .subscribe(soundEffects => {
        this._soundEffects = soundEffects;
        this._soundEffectsObs.next(soundEffects);
      });
  }

  get soundEffects(): SoundEffect[] {
    return this._soundEffects;
  }

  get soundEffectsObs(): Subject<SoundEffect[]> {
    return this._soundEffectsObs;
  }
}
