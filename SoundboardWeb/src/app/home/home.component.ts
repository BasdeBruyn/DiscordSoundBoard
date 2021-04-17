import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient, HttpParams} from '@angular/common/http';
import {AuthService} from '../auth.service';
import {Subscription} from 'rxjs';
import {DiscordService} from '../discord.service';
import {Guild} from '../models/guild';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  public guilds: Guild[] = [];
  public isAuthorized = false;
  private isAuthorizedObs: Subscription = new Subscription();

  constructor(public authService: AuthService,
              public discordService: DiscordService) {
  }

  ngOnInit(): void {
    this.isAuthorized = this.authService.isAuthorized;
    this.isAuthorizedObs = this.authService.isAuthorizedObs.subscribe(isAuthorized => this.isAuthorized = isAuthorized);

    this.discordService.getGuilds().subscribe(this.setGuilds.bind(this));
  }

  ngOnDestroy(): void {
    this.isAuthorizedObs.unsubscribe();
  }

  setGuilds(guilds: Guild[]): void {
    this.guilds = guilds;
  }
}
