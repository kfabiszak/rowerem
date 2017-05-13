import { Injectable } from '@angular/core';

import { Connectivity } from './connectivity';
import { Geolocation } from 'ionic-native';

declare var google;

@Injectable()
export class GoogleApi {

  mapElement: any;
  pleaseConnect: any;
  map: any;
  mapInitialised: boolean = false;
  markers: any = [];
  apiKey: string = 'AIzaSyBVeHZPeqnjmAHhMQrAo0j9DOdQ4lzkz-c';
  mapRequested: boolean = false;
  directionsService: any;
  directionsDisplay: any;
  autocompleteService: any;
  bounds: any;
  placesService: any;
  geocoder: any;
  lastPosition: any;
  lastLocation: any;
  ready: Promise<any>;
  readyResolve: any;
  styles: any;

  constructor(public connectivityService: Connectivity) {
    this.ready = new Promise((resolve) => { this.readyResolve = resolve });
  }

  getGoogle(): any {
    return google;
  }

  init(mapElement: any, pleaseConnect: any, styles: any): Promise<any> {
    this.mapRequested = true;

    this.styles = styles;
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
            script.src = 'https://maps.google.com/maps/api/js?key=' + this.apiKey + '&libraries=places&callback=mapInit';
          } else {
            script.src = 'https://maps.google.com/maps/api/js?&libraries=places&callback=mapInit';
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
      this.getPosition().then((position) => {

        this.lastPosition = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

        let mapOptions = {
          center: this.lastPosition,
          zoom: 15,
          mapTypeId: google.maps.MapTypeId.ROADMAP,
          styles: this.styles
        }

        this.map = new google.maps.Map(this.mapElement, mapOptions);

        this.geocoder = new google.maps.Geocoder();
        this.bounds = new google.maps.LatLngBounds();
        this.placesService = new google.maps.places.PlacesService(this.map);
        this.autocompleteService = new google.maps.places.AutocompleteService();

        this.directionsService = new google.maps.DirectionsService;

        this.directionsDisplay = [
          new google.maps.DirectionsRenderer({
            map: this.map,
            suppressMarkers: true,
            preserveViewport: true,
          })
        ];

        this.readyResolve();
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

  getPosition(cached: boolean = false): Promise<any> {
    return new Promise((resolve) => {
      if (cached && this.lastPosition) {
        resolve(this.lastPosition);
      } else {
        Geolocation.getCurrentPosition().then((position) => {
          this.lastPosition = position;
          resolve(this.lastPosition);
        })
      }
    })
  }

  toLatLng(position) {
    return new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
  }

  getLocation(cached: boolean = false): Promise<any> {
    return new Promise((resolve) => {
      if (cached && this.lastLocation) {
        resolve(this.lastLocation);
      } else {
        let latLng = this.lastPosition;
        this.geocoder.geocode({ latLng }, (results, status) => {
          if (status === "OK" && results[1]) {
            let city, country;
            for (var i = 0; i < results[0].address_components.length; i++) {
              for (var b = 0; b < results[0].address_components[i].types.length; b++) {
                if (results[0].address_components[i].types[b] == "locality") {
                  city = results[0].address_components[i];
                }
                if (results[0].address_components[i].types[b] == "country") {
                  country = results[0].address_components[i];
                }
              }
            }
            this.lastLocation = { city, country };
            resolve(this.lastLocation);
          }
        });
      }
    })
  }

  getDetails(place: any): Promise<any> {
    return new Promise((resolve) => {
      this.placesService.getDetails(place, (place, status) => {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
          let location = place.geometry.location;
          resolve({ lat: location.lat(), lng: location.lng() });
        }
      });
    })
  }

  addMarker(lat: number, lng: number, label: string = ''): void {
    let icon = 'https://maps.google.com/mapfiles/kml/shapes/horsebackriding.png';
    let position = new google.maps.LatLng(lat, lng);
    let marker = new google.maps.Marker({
      map: this.map,
      icon,
      animation: google.maps.Animation.DROP,
      position,
      label
    });
    this.markers.push(marker);
  }

  displayRoute(origin: string, stationStart: any, stationEnd: any, destination: string): Promise<any> {
    let waypoints = [
      { location: { lat: stationStart.lat, lng: stationStart.lng, stopover: true } },
      { location: { lat: stationEnd.lat, lng: stationEnd.lng }, stopover: true }
    ];
    return new Promise((resolve) => {
      this.directionsService.route({
        origin,
        destination,
        waypoints,
        optimizeWaypoints: true,
        travelMode: 'BICYCLING'
      }, (response, status) => {
        if (status == google.maps.DirectionsStatus.OK) {
          this.directionsDisplay[0].setDirections(response);
          this.map.setCenter(response.routes[0].bounds.getCenter());
          this.map.fitBounds((response.routes[0].bounds));
          if (this.map.getZoom() < 14)
            this.map.setZoom(14);
          resolve(response)
        } else {
          console.log('Directions request failed due to ' + status);
        }
      });
    })
  }

}
