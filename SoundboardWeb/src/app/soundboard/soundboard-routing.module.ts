import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SoundboardComponent} from './soundboard.component';
import {AuthGuard} from '../auth.guard';

const routes: Routes = [
  {
    path: 'soundboard', component: SoundboardComponent, canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SoundboardRoutingModule {
}
