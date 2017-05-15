import { Injectable } from '@angular/core';

@Injectable()
export class Dictionary {

  availableLanguages: string[] = [];
  language: string = '';

  constructor() { }

  public changeLanguage(lng: string): string {
    return this.language = lng;
  }

  public getActualLanguage(): string {
    return this.language;
  }

}
