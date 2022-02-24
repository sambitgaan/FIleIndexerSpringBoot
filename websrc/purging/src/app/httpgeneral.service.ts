import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, of, tap } from 'rxjs';
import { Config } from './models/config';
import { LoginData, LoginResponse, UserData } from './models/user';

interface QueryParams {
  [key: string]: string | number;
}

let httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': '1212sdcsda'
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

  login(data : LoginData): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.END_POINT + '/users/login', 
    JSON.stringify(data), httpOptions).pipe(tap((outData: LoginResponse) => console.log(`get user w/ id=${outData.status}`)),
      catchError(this.handleError<LoginResponse>('addedUser')));
  }

  saveUser(data : UserData): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.END_POINT + '/users/register', JSON.stringify(data), httpOptions).pipe(
      tap((updateHero: LoginResponse) => console.log(`saved user w/ id=${updateHero}`)),
      catchError(this.handleError<LoginResponse>('addedUser')));
  }

  logOut(){
    localStorage.clear();
  }

  getConfigDetails(): Observable<Config> {
    let userId = localStorage.getItem("loginUserId");
    return this.http.get<Config>(this.END_POINT + '/config/user/'+userId, httpOptions);
  }

  saveConfig(data : Config): Observable<Config> {
    return this.http.post<Config>(this.END_POINT + '/config/save', JSON.stringify(data), httpOptions).pipe(
      tap((updateHero: Config) => console.log(`saved config w/ id=${updateHero.configId}`)),
      catchError(this.handleError<Config>('addedConfig')));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
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
