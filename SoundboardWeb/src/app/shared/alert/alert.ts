export interface Alert {
  message: string;
  context: AlertContext;
}

export enum AlertContext {
  Info = 'info',
  Success = 'success',
  Error = 'error'
}
