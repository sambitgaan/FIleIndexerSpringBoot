import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { AlertService } from '../service/alert.service';

import { HttpgeneralService } from '../httpgeneral.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: any = {};
  loading = false;
  returnUrl: string = "";

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private httpService: HttpgeneralService,
    private alertService: AlertService
  ) { }

  ngOnInit() {
    // reset login status
    //this.authenticationService.logout();

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  login() {
    this.loading = true;
    localStorage.setItem("loginStatus", "true");
          localStorage.setItem("loginUser", this.model.username);
          this.router.navigate(["/"]);
    // this.httpService.login(this.model.username, this.model.password).subscribe(response => {
    //       localStorage.setItem("loginStatus", "true");
    //       localStorage.setItem("loginUser", this.model.username);
    //       this.router.navigate(["/"]);
    //     },
    //     error => {
    //       this.alertService.error(error);
    //       this.loading = false;
    //     });
  }

}
