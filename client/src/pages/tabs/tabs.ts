import { Component } from '@angular/core';
import { BrowserPage } from '../browser/browser';
import { MapPage } from '../map/map';

@Component({
  templateUrl: 'tabs.html'
})
export class TabsPage {

  tab1Root = MapPage;
  tab2Root = BrowserPage;

  constructor() {

  }
}
