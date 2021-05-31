import {Component, Input, OnInit} from '@angular/core';
import {SoundEffect} from '../../models/SoundEffect';
import {SoundboardService} from '../shared/soundboard.service';
import {AlertService} from '../../shared/alert/alert.service';
import {AlertContext} from '../../shared/alert/alert';

@Component({
  selector: 'app-sound-effect',
  templateUrl: './sound-effect.component.html',
  styleUrls: ['./sound-effect.component.scss']
})
export class SoundEffectComponent implements OnInit {
  @Input() public soundEffect: SoundEffect = new SoundEffect('', '');

  constructor(
    private musicplayerService: SoundboardService,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
  }

  playSoundEffect(): void {
    this.musicplayerService.playSoundEffect(this.soundEffect)
      .then(() => this.alertService.showAlert(`playing '${this.soundEffect.name}'`, AlertContext.Info))
      .catch(() => this.alertService.showAlert(`failed to play '${this.soundEffect.name}'`, AlertContext.Error));
  }
}
