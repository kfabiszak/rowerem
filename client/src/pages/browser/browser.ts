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

  private setActualLocation(cached: boolean): void {
    this.maps.getLocation(cached).then((location) => {
      this.location = location;
      this.loadedLocation = true;
    });
  }

  private setActualPosition(cached: boolean): void {
    this.maps.getPosition(cached).then((position) => {
      this.server.requestNearby(position).then((nearby) => {
        this.nearby = nearby;
        this.loadedNearby = true;
      })
    })
  }

  private navigateToStation(station: any): void {
    console.log('navigate to ', station);
  }

  private navigateRoute(points: any): void {
    this.server.requestRoute({
      origin: points.startPoint,
      destination: points.endPoint,
      location: this.location
    }).then((response) => {
      let result = response.json();
      this.maps.addMarker(result.startStationLat, result.startStationLng);
      this.maps.addMarker(result.endStationLat, result.endStationLng);
      this.maps.displayRoute(result.origin, { lat: result.startStationLat, lng: result.startStationLng },
        { lat: result.endStationLat, lng: result.endStationLng }, result.destination);
    });
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
