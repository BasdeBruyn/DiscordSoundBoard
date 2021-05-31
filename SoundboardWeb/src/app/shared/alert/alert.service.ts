import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Alert, AlertContext} from './alert';

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private _alertObservable = new Subject<Alert>();

  constructor() {
  }

  showAlert(message: string, context: AlertContext): void {
    this._alertObservable.next({message, context});
  }

  get alertObservable(): Subject<Alert> {
    return this._alertObservable;
  }
}
