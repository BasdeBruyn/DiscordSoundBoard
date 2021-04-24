import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';
import {Subscription} from 'rxjs';
import {SoundEffect} from '../models/SoundEffect';
import {SoundEffectService} from '../soundEffect.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  public soundEffects: SoundEffect[] = [];
  public isAuthorized = false;
  private isAuthorizedObs = new Subscription();
  private soundEffectsObs = new Subscription();

  constructor(public authService: AuthService, public soundEffectService: SoundEffectService) {
  }

  ngOnInit(): void {
    this.isAuthorized = this.authService.isAuthorized;
    this.isAuthorizedObs = this.authService.isAuthorizedObs.subscribe(isAuthorized => this.isAuthorized = isAuthorized);
    this.soundEffectsObs = this.soundEffectService.soundEffectsObs.subscribe(soundEffects => this.soundEffects = soundEffects);

    if (this.isAuthorized) {
      this.soundEffectService.retrieveSoundEffects();
    }
  }

  ngOnDestroy(): void {
    this.isAuthorizedObs.unsubscribe();
    this.soundEffectsObs.unsubscribe();
  }
}
