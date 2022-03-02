import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, of, tap, throwError } from 'rxjs';
import { Config, IndexResponse } from './models/config';
import { Router } from '@angular/router';
import { LoginData, LoginResponse, UserData, IndexRequest } from './models/user';
import {ToastService} from './toast-service';

interface QueryParams {
  [key: string]: string | number;
}

@Injectable({
  providedIn: 'root'
})
export class HttpgeneralService {
  private readonly END_POINT: string;

  constructor(private http: HttpClient, private router: Router,public toastService: ToastService) {
    this.END_POINT = 'http://localhost:8080';
  }


  startIndex(data: IndexRequest): Observable<IndexResponse> {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token") ? "Bearer " + localStorage.getItem("token") : ""
      })
    };
    return this.http.post<IndexResponse>(this.END_POINT + '/rb/startIndex', JSON.stringify(data), httpOptions).pipe(
      tap((updateHero: IndexResponse) => console.log(`Index generated w/ id=${updateHero}`)),
      catchError(this.handleError<IndexResponse>('Indexes generated')));
  }

  mergeIndexes(data: IndexRequest): Observable<IndexResponse> {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token") ? "Bearer " + localStorage.getItem("token") : ""
      })
    };
    return this.http.post<IndexResponse>(this.END_POINT + '/rb/startMerge', JSON.stringify(data), httpOptions).pipe(
      tap((updateHero: IndexResponse) => console.log(`Indexes Merged=${updateHero}`)),
      catchError(this.handleError<IndexResponse>('Indexes Merged')));
  }

  searchFile(data: IndexRequest): Observable<IndexResponse> {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token") ? "Bearer " + localStorage.getItem("token") : ""
      })
    };
    return this.http.post<IndexResponse>(this.END_POINT + '/rb/searchIndex', JSON.stringify(data), httpOptions).pipe(
      tap((updateHero: IndexResponse) => console.log(`Files paths generated=${updateHero}`)),
      catchError(this.handleError<IndexResponse>('FIle paths added')));
  }

  deleteSearchedFiles(data: IndexRequest): Observable<IndexResponse> {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token") ? "Bearer " + localStorage.getItem("token") : ""
      })
    };
    return this.http.post<IndexResponse>(this.END_POINT + '/rb/deleteFiles', JSON.stringify(data), httpOptions).pipe(
      tap((updateHero: IndexResponse) => console.log(`Files deleted=${updateHero}`)),
      catchError(this.handleError<IndexResponse>('Files deleted successfully')));
  }

  login(data: LoginData): Observable<LoginResponse> {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<LoginResponse>(this.END_POINT + '/users/authenticate',
      JSON.stringify(data), httpOptions).pipe(tap((outData: LoginResponse) => console.log(`get user w/ id=${outData.status}`)),
        catchError(this.handleError<LoginResponse>('addedUser')));
  }

  saveUser(data: UserData): Observable<LoginResponse> {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<LoginResponse>(this.END_POINT + '/users/register', JSON.stringify(data), httpOptions).pipe(
      tap((updateHero: LoginResponse) => console.log(`saved user w/ id=${updateHero}`)),
      catchError(this.handleError<LoginResponse>('addedUser')));
  }

  logOut() {
    localStorage.clear();
  }

  getConfigDetails(): Observable<Config> {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token") ? "Bearer " + localStorage.getItem("token") : ""
      })
    };
    let userId = localStorage.getItem("loginUserId");
    return this.http.get<Config>(this.END_POINT + '/config/user/' + userId, httpOptions);
  }

  saveConfig(data: Config): Observable<Config> {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token") ? "Bearer " + localStorage.getItem("token") : ""
      })
    };
    return this.http.post<Config>(this.END_POINT + '/config/save', JSON.stringify(data), httpOptions).pipe(
      tap((updateHero: Config) => console.log(`saved config w/ id=${updateHero.configId}`)),
      catchError(this.handleError<Config>('addedConfig')));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      if (error.status === 401 || error.status === 403) {
        this.toastService.show("UnAuthorization request. Please login and try again.", { classname: 'bg-danger text-light', delay: 10000 });
        //navigate /delete cookies or whatever
        this.router.navigateByUrl(`/login`);
        // TODO: send the error to remote logging infrastructure
        console.log(error); // log to console instead
        // TODO: better job of transforming error for user consumption
        console.log(`${operation} failed: ${error.message}`);
      }
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
