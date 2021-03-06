import { Component, NgZone, Output, EventEmitter } from '@angular/core';
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

  @Output() navigate = new EventEmitter;
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

  private updateByHistory(hisory: any) {
    this.startPoint.text = hisory.startPoint;
    this.endPoint.text = hisory.endPoint;
  }

  private saveFavorite(startPoint, endPoint) {
    this.storage.updateElFavorite(startPoint, endPoint);
  }

  private saveHistory(startPoint, endPoint) {
    this.storage.updateElHistory(this.startPoint.text, this.endPoint.text);
  }

  private setCoords(point: any) {
    return this.maps.getDetails(point.place).then(latLng => {
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
            point.text = results[0].formatted_address;
            resolve(results[0]);
          }
        })
      })
    }).then((result: any) => {
      point.searching = false;
    })
  }

  private searchAccept() {
    Promise.all([this.updatePlace(this.startPoint), this.updatePlace(this.endPoint)]).then(() => {
      this.autocompleteItems = [];
      if (this.loaded && this.startPoint.text && this.endPoint.text) {
        this.saveHistory(this.startPoint, this.endPoint);
        // this.saveFavorite(this.startPoint, this.endPoint);
        this.navigate.emit({ startPoint: this.startPoint, endPoint: this.endPoint });
      }
    });
  }

  private updatePlace(point: any) {
    return new Promise((resolve) => {
      this.maps.autocompleteService.getPlacePredictions({
        input: `${this.location.city.long_name} ${point.text}`,
        componentRestrictions: { country: this.location.country.short_name }
      }, (predictions, status) => {
        if (status === "OK") {
          point.text = predictions[0].description;
          point.place = predictions[0];
          this.setCoords(point).then(() => {
            resolve(true);
          });
        }
      });
    });
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
