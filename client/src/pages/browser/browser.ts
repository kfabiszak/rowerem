import { Component, NgZone } from '@angular/core';
import { NavController, Platform } from 'ionic-angular';
import { GoogleApi } from '../../providers/google-api';
import { ServerApi } from '../../providers/server-api';
import { SearchModule } from '../../components/search';

@Component({
  selector: 'page-browser',
  templateUrl: 'browser.html',
})
export class BrowserPage {

  constructor(public navCtrl: NavController, public maps: GoogleApi, public server: ServerApi, public platform: Platform) {

  }

  ionViewDidLoad() {
    this.platform.ready().then(() => {
      this.maps.ready.then(() => {
        //Map loaded

      });
    });
  }

}
