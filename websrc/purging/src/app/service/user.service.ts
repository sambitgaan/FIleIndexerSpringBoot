import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpHeaders, HttpResponse, HttpParams} from '@angular/common/http';

import { UserData } from '../models/user';

import { map } from "rxjs/operators"; 


const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
      })
    };
    
@Injectable()
export class UserService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get('/api/users').subscribe(res => res);
        //, get('/api/users/' + id, this.jwt()).map((response: Response) => response.json())
    }

    getById(id: number) {
        return this.http.get('/api/users/' + id).subscribe(res => res);
    }

    create(user: UserData) {
        return this.http.post('/api/users', user).subscribe(res => res);
    }

    update(user: UserData) {
        return this.http.put('/api/users/' + user.userId, user).subscribe(res => res);
    }

    delete(id: number) {
        return this.http.delete('/api/users/' + id).subscribe(res => res);
    }

    // private helper methods

    // private jwt() {
    //     // create authorization header with jwt token
    //     let currentUser = JSON.parse(localStorage.getItem('currentUser') ? localStorage.getItem('currentUser'): "");
    //     if (currentUser && currentUser.token) {
    //         let headers = new HttpHeaders({ 'Authorization': 'Bearer ' + currentUser.token });
    //         return new RequestOptions({ headers: headers });
        
    // }
}