import { Component, NgZone } from '@angular/core';
import { Platform } from 'ionic-angular';
import { GoogleApi } from '../../providers/google-api';
import { ServerApi } from '../../providers/server-api';
import { Storage } from '../../providers/storage';

@Component({
  selector: 'search',
  templateUrl: 'search.html',
  inputs: ['location', 'loaded']
})
export class SearchComponent {

  private startPoint = { searching: false, text: "", place: {}, latLng: {} };
  private endPoint = { searching: false, text: "", place: {}, latLng: {} };
  private focusedPoint: any;
  private location: any;
  private loaded: boolean = false;
  private autocompleteItems: any;

  constructor(public maps: GoogleApi, public server: ServerApi, public storage: Storage, public platform: Platform, private zone: NgZone) {

  }

  private changeFocus(point: any) {
    this.focusedPoint = point;
  }

  private updateInput(point: any) {
    this.updateSearch(point.text);
  }

  private updateByHint(hint: any) {
    this.focusedPoint.text = hint.description;
    this.focusedPoint.place = hint;
    this.autocompleteItems = [];
    this.setCoords(this.focusedPoint);
  }

  updateByHistory(hisory: any) {
    this.startPoint.text = hisory.startPoint;
    this.endPoint.text = hisory.endPoint;
  }

  private setCoords(point: any) {
    this.maps.getDetails(point.place).then(latLng => {
      point.latLng = latLng;
    })
  }

  private setActualPosition(point: any) {
    point.searching = true;
    this.autocompleteItems = [];
    new Promise((resolve) => {
      this.maps.getPosition().then((position) => {
        const latLng = this.maps.toLatLng(position);
        this.maps.geocoder.geocode({ latLng }, (results, status) => {
          if (status === "OK" && results[0]) {
            resolve(results[0]);
          }
        })
      })
    }).then((result: any) => {
      point.text = result.formatted_address;
      point.searching = false;
    })
  }

  private searchAccept() {
    if (this.autocompleteItems.length)
      this.updateByHint(this.autocompleteItems[0]);
    if (this.loaded && this.startPoint.text && this.endPoint.text) {
      this.storage.updateHistory(this.startPoint.text, this.endPoint.text);
      this.server.requestRoute({
        origin: this.startPoint,
        destination: this.endPoint,
        location: this.location
      }).then((response) => {
        let result = response.json();
        this.maps.addMarker(result.startStationLat, result.startStationLng);
        this.maps.addMarker(result.endStationLat, result.endStationLng);
        this.maps.displayRoute(result.origin, { lat: result.startStationLat, lng: result.startStationLng },
          { lat: result.endStationLat, lng: result.endStationLng }, result.destination);
      });
    }
  }

  private updateSearch(query: String = '') {
    if (query == '') {
      this.autocompleteItems = [];
    } else if (this.loaded) {
      this.maps.autocompleteService.getPlacePredictions({
        input: `${this.location.city.long_name} ${query}`,
        componentRestrictions: { country: this.location.country.short_name }
      }, (predictions, status) => {
        this.autocompleteItems = [];
        this.zone.run(() => {
          if (status === "OK") //#todo google.maps.places.PlacesServiceStatus.OK
            predictions.forEach((prediction) => {
              this.autocompleteItems.push(prediction);
            });
        });
      });
    }
  }
}
