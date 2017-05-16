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
  private routeMarkers: any = [];

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
        this.nearby = nearby.json();
        this.loadedNearby = true;
      })
    })
  }

  private clearRouteMarkers(): void {
    this.maps.clearMarkers(this.routeMarkers);
    this.routeMarkers = [];
  }

  private navigateToStation(station: any): void {
    this.clearRouteMarkers()
    let icon = `${this.maps.iconHost}&chld=S|22FF22|FFFFFF`;
    this.routeMarkers.push(this.maps.addMarker(this.location.latLng.lat(), this.location.latLng.lng(), icon));
    icon = `${this.maps.iconHost}&chld=${station.bikes}|FF2222|FFFFFF`;
    this.routeMarkers.push(this.maps.addMarker(station.lat, station.lng, icon));
    this.maps.displayRoute(station, null, null, this.location.latLng);
  }

  private navigateRoute(points: any): void {
    this.server.requestRoute({
      origin: points.startPoint,
      destination: points.endPoint,
      location: this.location
    }).then((response) => {
      this.clearRouteMarkers()
      let result = response.json(); //#TODO refactoring
      let icon = `${this.maps.iconHost}&chld=1|0f82ff|FFFFFF`;
      this.routeMarkers.push(this.maps.addMarker(result.startStationLat, result.startStationLng, icon));
      icon = `${this.maps.iconHost}&chld=2|0f82ff|FFFFFF`;
      this.routeMarkers.push(this.maps.addMarker(result.endStationLat, result.endStationLng, icon));
      icon = `${this.maps.iconHost}&chld=S|22FF22|FFFFFF`;
      this.routeMarkers.push(this.maps.addMarker(points.startPoint.latLng.lat, points.startPoint.latLng.lng, icon));
      icon = `${this.maps.iconHost}&chld=E|FF2222|FFFFFF`;
      this.routeMarkers.push(this.maps.addMarker(points.endPoint.latLng.lat, points.endPoint.latLng.lng, icon));
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
