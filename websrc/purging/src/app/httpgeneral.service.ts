import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Config } from './models/config';

interface QueryParams {
  [key: string]: string | number;
}

let httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': '1212sdcsda',
    'x-header': 'x-value'
  })
}

@Injectable({
  providedIn: 'root'
})
export class HttpgeneralService {
  private readonly END_POINT: string;

  constructor(private http: HttpClient) {
    this.END_POINT = 'http://localhost:8080';
  }


  startIndex() {
    return this.http.get<"">(this.END_POINT + '/rb/startIndex');
  }

  mergeIndexes() {
    return this.http.get<"">(this.END_POINT + '/rb/startMerge');
  }

  searchFile() {
    return this.http.get<"">(this.END_POINT + '/rb/searchIndex');
  }

  deleteSearchedFiles() {
    return this.http.get<"">(this.END_POINT + '/rb/deleteFiles');
  }

  login(username: string, password: string) {
    return this.http.post(this.END_POINT + '/users/login', JSON.stringify({ userName: username, password: password }), httpOptions);
  }

  getConfigDetails(): Observable<Config> {
    return this.http.get<Config>(this.END_POINT + '/config/user/1', httpOptions)
    .pipe(tap(_ => console.log(`fetched hero id=${id}`)),catchError(this.handleError<Config>(`getHero id`)));
  }

  handleError<T>(arg0: string): (err: any, caught: Observable<Config>) => import("rxjs").ObservableInput<any> {
    throw new Error('Method not implemented.');
  }

  //   private getGroups() : Observable<any> {
  //     var responseAsObject : any;
  //     return this.http.get(this.END_POINT + '/config/user/1', { headers: headers }).map(response => {
  //             var responseAsObject = response.json();
  //             return responseAsObject;
  //         });
  // }

  // getRemove<returnType>(
  //   id: number | null,
  //   route: string,
  //   qp: QueryParams = {},
  //   method: 'get' | 'delete' = 'get'
  // ): Observable<returnType> {
  //   return this.http[method](
  //     `${this.END_POINT}/${route}${id ? '/' + id : ''}${cfqu}`
  //   ) as Observable<returnType>;
  // }

}
