import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RedirectComponent} from './redirect/redirect.component';
import {HomeComponent} from './home/home.component';
import {HttpClientModule} from '@angular/common/http';
import {AuthService} from './auth.service';
import {SoundEffectService} from './soundEffect.service';
import {AuthGuard} from './auth.guard';
import {SoundboardModule} from './soundboard/soundboard.module';
import { NavbarComponent } from './navbar/navbar.component';
import { AlertComponent } from './alert/alert.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AlertService} from './shared/alert/alert.service';

@NgModule({
  declarations: [
    AppComponent,
    RedirectComponent,
    HomeComponent,
    NavbarComponent,
    AlertComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    SoundboardModule,
    BrowserAnimationsModule
  ],
  providers: [
    AuthGuard,
    AuthService,
    SoundEffectService,
    AlertService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
