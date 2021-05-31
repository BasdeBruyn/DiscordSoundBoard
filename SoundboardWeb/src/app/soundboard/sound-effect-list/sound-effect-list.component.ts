import {Component, OnDestroy, OnInit} from '@angular/core';
import {SoundEffect} from '../../models/SoundEffect';
import {SoundEffectService} from '../../soundEffect.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-sound-effect-list',
  templateUrl: './sound-effect-list.component.html',
  styleUrls: ['./sound-effect-list.component.scss']
})
export class SoundEffectListComponent implements OnInit, OnDestroy {
  public soundEffects: SoundEffect[] = [];
  private soundEffectsObs = new Subscription();

  constructor(
    private soundEffectService: SoundEffectService
  ) {
  }

  ngOnInit(): void {
    this.soundEffectsObs = this.soundEffectService.soundEffectsObs.subscribe(soundEffects => this.soundEffects = soundEffects);
    this.soundEffectService.retrieveSoundEffects();
  }

  ngOnDestroy(): void {
    this.soundEffectsObs.unsubscribe();
  }
}
