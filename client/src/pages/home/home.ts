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
  private startPoint = { focused: false, text: "", options: {} };
  private endPoint = { focused: false, text: "", options: {} };
  private googleReady = false;

  constructor(public navCtrl: NavController, public maps: GoogleApi, public server: ServerApi, public platform: Platform, private loadingCtrl: LoadingController) {

  }

  private changeFocus() {

  }

  private updateInput() {

  }

  private searchAccept() {
    if (this.googleReady) {
      console.log(this.startPoint, this.startPoint.text);
      this.server.requestRoute({
        origin: this.startPoint.text,
        destination: this.endPoint.text
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
        this.googleReady = true;
        loadingPopup.dismiss();
      });
    });
  }

}
