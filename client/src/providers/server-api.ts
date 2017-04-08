import { Injectable } from '@angular/core';
import { Connectivity } from './connectivity';
import { Geolocation } from 'ionic-native';

declare var google;

@Injectable()
export class ServerApi {

  mapElement: any;
  pleaseConnect: any;
  map: any;
  mapInitialised: boolean = false;
  markers: any = [];
  apiKey: string;
  mapRequested: boolean = false;
  scriptsLoaded: boolean = false;
  directionsService: any;
  directionsDisplay: any;
  bounds: any;
  placesService: any;
  geocoder: any;
  initPosition: any;

  constructor(public connectivityService: Connectivity) {

  }

  getGoogle(): any {
    return google;
  }

  init(mapElement: any, pleaseConnect: any): Promise<any> {
    this.mapRequested = true;

    this.mapElement = mapElement;
    this.pleaseConnect = pleaseConnect;

    return this.loadGoogleMaps();
  }

  loadGoogleMaps(): Promise<any> {
    return new Promise((resolve) => {
      if (typeof google == "undefined" || typeof google.maps == "undefined") {
        console.log("Google maps JavaScript needs to be loaded.");
        this.disableMap();
        if (this.connectivityService.isOnline()) {
          window['mapInit'] = () => {
            this.initMap().then(() => {
              resolve(true);
            });
            this.enableMap();
          }
          let script = document.createElement("script");
          script.id = "googleMaps";
          if (this.apiKey) {
            script.src = 'http://maps.google.com/maps/api/js?key=' + this.apiKey + '&libraries=places&callback=mapInit';
          } else {
            script.src = 'http://maps.google.com/maps/api/js?&libraries=places&callback=mapInit';
          }
          document.body.appendChild(script);
        }
      } else {
        if (this.connectivityService.isOnline()) {
          this.initMap();
          this.enableMap();
        } else {
          this.disableMap();
        }
      }
      this.addConnectivityListeners();
    });

  }

  initMap(): Promise<any> {

    this.mapInitialised = true;

    return new Promise((resolve) => {

      Geolocation.getCurrentPosition().then((position) => {

        this.initPosition = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

        let mapOptions = {
          center: this.initPosition,
          zoom: 15,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        }

        this.map = new google.maps.Map(this.mapElement, mapOptions);
        this.geocoder = new google.maps.Geocoder();
        this.bounds = new google.maps.LatLngBounds();
        this.placesService = new google.maps.places.PlacesService(this.map);

        this.directionsService = new google.maps.DirectionsService;
        this.directionsDisplay = [
          new google.maps.DirectionsRenderer({
            map: this.map,
            suppressMarkers: true,
            preserveViewport: true,
          })
        ];
        resolve(true);
      });
    });
  }

  disableMap(): void {
    if (this.pleaseConnect) {
      this.pleaseConnect.style.display = "block";
    }
  }

  enableMap(): void {
    if (this.pleaseConnect) {
      this.pleaseConnect.style.display = "none";
    }
  }

  addConnectivityListeners(): void {
    document.addEventListener('online', () => {
      console.log("online");
      setTimeout(() => {
        if (typeof google == "undefined" || typeof google.maps == "undefined") {
          this.loadGoogleMaps();
        } else {
          if (!this.mapInitialised) {
            this.initMap();
          }
          this.enableMap();
        }
      }, 2000);
    }, false);
    document.addEventListener('offline', () => {
      console.log("offline");
      this.disableMap();
    }, false);

  }
}
