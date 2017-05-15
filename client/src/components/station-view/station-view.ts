import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'station-view',
  templateUrl: 'station-view.html',
  inputs: ['station', 'loaded']
})
export class StationViewComponent {

  @Output() navigate = new EventEmitter;
  private station: any;
  private loaded: boolean = false;

  constructor() {

  }

  private gotoStation(): void {
    this.navigate.emit(this.station);
  }

}
