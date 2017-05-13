import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';

import { TabsPage } from '../pages/tabs/tabs';
import { BrowserPage } from '../pages/browser/browser';
import { MapPage } from '../pages/map/map';

import { SearchComponent } from '../components/search/search';
import { StationViewComponent } from '../components/station-view/station-view';
import { HistoryComponent } from '../components/history/history';

import { ServerApi } from '../providers/server-api';
import { GoogleApi } from '../providers/google-api';
import { Connectivity } from '../providers/connectivity';
import { Storage } from '../providers/storage';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

@NgModule({
  declarations: [
    MyApp,
    TabsPage,
    BrowserPage,
    MapPage,
    SearchComponent,
    StationViewComponent,
    HistoryComponent
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp),
    HttpModule
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    TabsPage,
    BrowserPage,
    MapPage,
    SearchComponent,
    StationViewComponent,
    HistoryComponent
  ],
  providers: [
    StatusBar,
    SplashScreen,
    ServerApi,
    GoogleApi,
    Connectivity,
    Storage,
    { provide: ErrorHandler, useClass: IonicErrorHandler }
  ]
})
export class AppModule { }
