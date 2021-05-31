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

  private static fuzzySearch(searchTerm: string, soundEffect: SoundEffect): boolean {
    const pattern = searchTerm.toLowerCase().split('').reduce((a, b) => a + '.*' + b);
    return (new RegExp(pattern)).test(soundEffect.name.toLowerCase());
  }

  retrieveSoundEffects(): void {
    const headers = {Authorization: 'Bearer ' + this.authService.jwtToken};
    this.httpClient.get<SoundEffect[]>(environment.apiBaseUrl + '/sound_effect', {headers})
      .subscribe(soundEffects => {
        this._soundEffects = soundEffects;
        this._soundEffectsObs.next(soundEffects);
      });
  }

  search(searchTerm: string): void {
    let value = this._soundEffects;
    if (searchTerm) {
      value = this.soundEffects.filter(soundEffect => SoundEffectService.fuzzySearch(searchTerm, soundEffect));
    }
    this.soundEffectsObs.next(value);
  }

  get soundEffectsObs(): Subject<SoundEffect[]> {
    return this._soundEffectsObs;
  }

  get soundEffects(): SoundEffect[] {
    return this._soundEffects;
  }
}
