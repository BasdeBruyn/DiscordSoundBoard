import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RedirectComponent} from './redirect/redirect.component';
import {HomeComponent} from './home/home.component';
import {HttpClientModule} from '@angular/common/http';
import {AuthService} from './auth.service';
import {DiscordService} from './discord.service';

@NgModule({
  declarations: [
    AppComponent,
    RedirectComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
    AuthService,
    DiscordService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
