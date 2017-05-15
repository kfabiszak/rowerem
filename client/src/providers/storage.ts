import { Injectable } from '@angular/core';

@Injectable()
export class Storage {

  history: any[] = [];

  constructor() { }

  public updateElHistory(startPoint, endPoint): void {
    if (!this.history.find((el) => {
      return el.startPoint == startPoint && el.endPoint == endPoint;
    })) {
      this.history.push({ startPoint, endPoint });
    }
  }

  public removeElHistory(point): void {
    const index = this.history.findIndex((el) => {
      return el.startPoint === point.startPoint.text && el.endPoint === point.endPoint.text;
    });
    if (index) {
      this.history.splice(index, 1);
    }
  }

  public getHistory(): any {
    return this.history;
  }

}
