import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions, URLSearchParams, Headers } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class ServerApi {

  private apiUrl: string = "http://0.0.0.0:4567/";

  constructor(public http: Http) {

  }

  public requestRoute(value: any) {
    return this.post('route', value);
  }

  public requestNearby(position: any) {
    return new Promise((resolve) => {
      resolve({ name: 'Politechnika Poznańska', freeBikes: 8, distance: '800m' });
    })
  }

  private get(route: string) {
    return this.http.get(this.apiUrl + route)
      .toPromise()
      .then((response: any) => {
        return response.json();
      })
      .catch(this.handleError);
  }

  private post(route: string, value: any): any {
    let packageData = {
      origin: value.origin.text,
      originLat: value.origin.latLng.lat,
      originLng: value.origin.latLng.lng,
      destination: value.destination.text,
      destinationLat: value.destination.latLng.lat,
      destinationLng: value.destination.latLng.lng,
      country_name: value.location.country.short_name,
      city_name: value.location.city.long_name
    };
    return this.http.post(this.apiUrl + route, JSON.stringify(packageData))
      .toPromise()
      .catch(this.handleError);
  }

  private handleError(error: Response | any) {
    console.log(error);
    // return Promise.reject(error);
    // let errMsg: string;
    // if (error instanceof Response) {
    //   const body = error.json() || '';
    //   const err = body.error || JSON.stringify(body);
    //   errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    // } else {
    //   errMsg = error.message ? error.message : error.toString();
    // }
    // console.error(errMsg);
    // return Promise.reject(errMsg);
  }
}
