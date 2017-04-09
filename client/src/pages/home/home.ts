import { Component, ElementRef, ViewChild } from '@angular/core';
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

  constructor(public navCtrl: NavController, public maps: GoogleApi, public server: ServerApi, public platform: Platform, private loadingCtrl: LoadingController) {

  }

  ionViewDidLoad() {

    let loadingPopup = this.loadingCtrl.create({
      content: 'Loading google map ...'
    });

    loadingPopup.present();

    this.platform.ready().then(() => {
      let mapLoaded = this.maps.init(this.mapElement.nativeElement, this.pleaseConnect.nativeElement);
      Promise.all([mapLoaded]).then((result) => {
        this.server.requestRoute(null);
        loadingPopup.dismiss();
      });
    });
  }

}
