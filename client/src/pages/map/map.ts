import { Component, ElementRef, ViewChild } from '@angular/core';
import { NavController, Platform, LoadingController } from 'ionic-angular';
import { GoogleApi } from '../../providers/google-api';

import { styleGray } from './styles';

@Component({
  selector: 'page-map',
  templateUrl: 'map.html',
})

export class MapPage {
  @ViewChild('map') mapElement: ElementRef;
  @ViewChild('pleaseConnect') pleaseConnect: ElementRef;

  constructor(public navCtrl: NavController, public maps: GoogleApi, public platform: Platform, private loadingCtrl: LoadingController) {

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
      });
    });
  }

}
