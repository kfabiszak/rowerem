import { Component, ElementRef, ViewChild } from '@angular/core';
import { NavController, Platform, LoadingController } from 'ionic-angular';
import { GoogleApi } from '../../providers/google-api';

import { styleGray } from './styles';
import { ServerApi } from '../../providers/server-api';

@Component({
  selector: 'page-map',
  templateUrl: 'map.html',
})

export class MapPage {
  @ViewChild('map') mapElement: ElementRef;
  @ViewChild('pleaseConnect') pleaseConnect: ElementRef;

  private stations: any;
  private buttonAction: string = "LOADING STATIONS...";
  private markerShow: boolean;
  private stationMarkers: any = [];

  constructor(public navCtrl: NavController, public maps: GoogleApi, public server: ServerApi, public platform: Platform, private loadingCtrl: LoadingController) {

  }

  private updateStations(): void {
    this.maps.getLocation(true).then(location => {
      this.server.requestAllStations(location).then(stations => {
        this.stations = stations.json();
        this.addAllStationMarkers();
      });
    });
  }

  private addAllStationMarkers(): void {
    this.stationMarkers = [];
    this.stations.map(station => {
      const img = `${this.maps.iconHost}&chld=${station.bikes}|1E90FF|FFFFFF`;
      this.stationMarkers.push(this.maps.addMarker(station.lat, station.lng, img));
    });
    this.toggleStations(true);
  }

  private toggleStations(visible: boolean = this.markerShow): void {
    if (visible) {
      this.buttonAction = 'SHOW STATIONS';
      this.maps.clearMarkers(this.stationMarkers);
    } else {
      this.buttonAction = 'HIDE STATIONS';
      this.maps.showMarkers(this.stationMarkers);
    }
    this.markerShow = !visible;
  }

  ionViewDidLoad() {
    let loadingPopup = this.loadingCtrl.create({
      content: 'Loading google map ...'
    });
    loadingPopup.present();

    this.platform.ready().then(() => {
      let mapLoaded = this.maps.init(this.mapElement.nativeElement, this.pleaseConnect.nativeElement, styleGray);

      Promise.all([mapLoaded]).then((result) => {
        loadingPopup.dismiss();
        this.updateStations();
      });
    });
  }

}
