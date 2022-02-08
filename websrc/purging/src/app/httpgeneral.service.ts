import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface QueryParams {
  [key: string]: string | number;
}

@Injectable({
  providedIn: 'root'
})

export class HttpgeneralService {
  private readonly END_POINT: string; 
  constructor(private http: HttpClient) {
    this.END_POINT = 'http://localhost:8080/rb';
  }


  startIndex() {
    return this.http.get<"">(this.END_POINT+'/startIndex');
  }

  mergeIndexes() {
    return this.http.get<"">(this.END_POINT+'/startMerge');
  }

  searchFile() {
    return this.http.get<"">(this.END_POINT+'/searchIndex');
  }

  deleteSearchedFiles() {
    return this.http.get<"">(this.END_POINT+'/deleteFiles');
  }

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
