import { Component } from '@angular/core';

@Component({
  selector: 'station-view',
  templateUrl: 'station-view.html',
  inputs: ['station', 'loaded']
})
export class StationViewComponent {

  private station: any;
  private loaded: boolean = false;

  constructor() {

  }

}
