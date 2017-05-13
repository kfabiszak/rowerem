import { Injectable } from '@angular/core';

@Injectable()
export class Storage {

  history: any[] = [];

  constructor() { }

  public updateHistory(startPoint, endPoint): void {
    this.history.push({ startPoint, endPoint });
  }

  public getHistory(): any {
    return this.history;
  }

}
