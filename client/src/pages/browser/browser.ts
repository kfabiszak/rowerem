import { Component } from '@angular/core';
import { NavController, Platform } from 'ionic-angular';
import { GoogleApi } from '../../providers/google-api';
import { ServerApi } from '../../providers/server-api';

@Component({
  selector: 'page-browser',
  templateUrl: 'browser.html',
})
export class BrowserPage {

  private location: any;
  private nearby: any;
  private loadedLocation: boolean;
  private loadedNearby: boolean;

  constructor(public navCtrl: NavController, public maps: GoogleApi, public server: ServerApi, public platform: Platform) {

  }

  setActualLocation(cached: boolean) {
    this.maps.getLocation(cached).then((location) => {
      this.location = location;
      this.loadedLocation = true;
    });
  }

  setActualPosition(cached: boolean) {
    this.maps.getPosition(cached).then((position) => {
      this.server.requestNearby(position).then((nearby) => {
        this.nearby = nearby;
        this.loadedNearby = true;
      })
    })
  }

  ionViewDidLoad() {
    this.platform.ready().then(() => {
      this.maps.ready.then(() => {
        this.setActualLocation(true);
        this.setActualPosition(true);
      });
    });
  }

}
