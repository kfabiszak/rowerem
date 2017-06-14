import { Injectable } from '@angular/core';

@Injectable()
export class Storage {

  history: any[];
  favorite: any[] = [];

  constructor() {
    this.fetchHistory();
    this.updateFavoite();
  }

  public updateElHistory(startPoint, endPoint, favorite = false): void {
    if (!this.history.find((el) => {
      return el.startPoint == startPoint && el.endPoint == endPoint;
    })) {
      this.history.push({ startPoint, endPoint, isFavorite: favorite });
      this.updateFavoite();
      this.saveHistory();
    }
  }

  public removeElHistory(point): void {
    const index = this.history.findIndex((el) => {
      return el.startPoint === point.startPoint.text && el.endPoint === point.endPoint.text;
    });
    if (index) {
      this.history.splice(index, 1);
      this.updateFavoite();
      this.saveHistory();
    }
  }

  public saveHistory(): void {
    localStorage.setItem('history', JSON.stringify({ data: this.history }));
  }

  public fetchHistory(): void {
    const storage = JSON.parse(localStorage.getItem('history'));
    this.history = storage ? storage.data : [];
  }

  public getHistory(): any {
    return this.history;
  }

  public updateElFavorite(startPoint, endPoint): void {
    const el = this.findElement(startPoint, endPoint);
    if (el) {
      el.isFavorite = !el.isFavorite;
    } else {
      this.updateElHistory(startPoint, endPoint, true);
    }
    this.updateFavoite();
    this.saveHistory();
  }

  public removeElFavorite(point): void {
    const el = this.findElement(point.startPoint, point.endPoint);
    if (el) {
      el.isFavorite = false;
      this.updateFavoite();
      this.saveHistory();
    }
  }

  public findElement(startPoint, endPoint): any {
    return this.history.find(el => { return el.startPoint == startPoint && el.endPoint == endPoint; });
  }

  public updateFavoite(): void {
    this.favorite.splice(0, this.favorite.length)
    this.favorite.push(...this.history.filter(el => el.isFavorite));
  }

  public getFavorite(): any {
    return this.favorite;
  }

}
