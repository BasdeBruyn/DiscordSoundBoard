import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SoundEffectListComponent} from './sound-effect-list/sound-effect-list.component';
import {SoundEffectComponent} from './sound-effect/sound-effect.component';
import {SoundboardComponent} from './soundboard.component';
import {SoundboardRoutingModule} from './soundboard-routing.module';
import { SearchBarComponent } from './search-bar/search-bar.component';
import {FormsModule} from '@angular/forms';


@NgModule({
  declarations: [
    SoundEffectListComponent,
    SoundEffectComponent,
    SoundboardComponent,
    SearchBarComponent
  ],
  imports: [
    CommonModule,
    SoundboardRoutingModule,
    FormsModule
  ]
})
export class SoundboardModule {
}
