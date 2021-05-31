import {Component, OnDestroy, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {AlertService} from '../shared/alert/alert.service';
import {Subscription} from 'rxjs';
import {AlertContext} from '../shared/alert/alert';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss'],
  animations: [
    trigger('alertState', [
      state('show', style({
        opacity: 1
      })),
      state('hide', style({
        opacity: 0
      })),
      transition('show => hide', animate('600ms ease-out')),
      transition('hide => show', animate('200ms ease-out'))
    ])
  ]
})
export class AlertComponent implements OnInit, OnDestroy {
  private _show = false;
  private _message = '';
  private _context = AlertContext.Info;
  private _alertSub = new Subscription();

  constructor(
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
    this._alertSub = this.alertService.alertObservable.subscribe(alert => {
      this._message = alert.message;
      this._context = alert.context;
      this._show = true;
      setTimeout(() => this._show = false, 3000);
    });
  }

  ngOnDestroy(): void {
    this._alertSub.unsubscribe();
  }

  toggle(): void {
    this._show = !this._show;
  }

  get message(): string {
    return this._message;
  }

  get stateName(): string {
    return this._show && this._message ? 'show' : 'hide';
  }

  get show(): boolean {
    return this._show;
  }

  get context(): AlertContext {
    return this._context;
  }
}
