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

  private get(route: string) {
    return this.http.get(this.apiUrl + route)
      .toPromise()
      .then((response: any) => {
        return response.json();
      })
      .catch(this.handleError);
  }

  private post(route: string, value: any): any {
    // let params = new URLSearchParams();
    // params.append('param1', 'name1');
    console.log(JSON.stringify(value));
    return this.http.post(this.apiUrl + route, JSON.stringify(value))
      .toPromise()
      .then((response: any) => {
        console.log(response);
      }
      ).catch(this.handleError);
  }

  private handleError(error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Promise.reject(errMsg);
  }
}
