import { Component, ElementRef, ViewChild, NgZone } from '@angular/core';
import { NavController, Platform, LoadingController } from 'ionic-angular';
import { GoogleApi } from '../../providers/google-api';
import { ServerApi } from '../../providers/server-api';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
})
export class HomePage {
  @ViewChild('map') mapElement: ElementRef;
  @ViewChild('pleaseConnect') pleaseConnect: ElementRef;
  private startPoint = { focused: false, text: "", place: {}, latLng: {} };
  private endPoint = { focused: false, text: "", place: {}, latLng: {} };
  private focusedPoint: any;
  private location: any;
  private ready: boolean = false;
  private autocompleteItems: any;

  constructor(public navCtrl: NavController, public maps: GoogleApi, public server: ServerApi, public platform: Platform, private loadingCtrl: LoadingController, private zone: NgZone) {

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

  private setCoords(point: any) {
    this.maps.getDetails(point.place).then(latLng => {
      point.latLng = latLng;
    })
  }

  private searchAccept() {
    if (this.autocompleteItems.length)
      this.updateByHint(this.autocompleteItems[0]);
    if (this.ready && this.startPoint.text && this.endPoint.text) {
      this.server.requestRoute({
        origin: this.startPoint,
        destination: this.endPoint,
        location: this.location
      });
    }
  }

  private updateSearch(query: String = '') {
    if (query == '') {
      this.autocompleteItems = [];
    } else {
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

  ionViewDidLoad() {
    let loadingPopup = this.loadingCtrl.create({
      content: 'Loading google map ...'
    });
    loadingPopup.present();

    this.platform.ready().then(() => {
      let mapLoaded = this.maps.init(this.mapElement.nativeElement, this.pleaseConnect.nativeElement);
      Promise.all([mapLoaded]).then((result) => {
        loadingPopup.dismiss();
        this.maps.getLocation().then((location) => {
          this.location = location;
          this.ready = true;
        });
      });
    });
  }

}
