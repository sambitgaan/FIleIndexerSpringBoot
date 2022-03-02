import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { AlertService } from '../service/alert.service';
import {ToastService} from '../toast-service';
import { NgxSpinnerService } from "ngx-spinner";
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
  responseMes: string = "";

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private httpService: HttpgeneralService,
    private alertService: AlertService,  private spinner: NgxSpinnerService,public toastService: ToastService
  ) { }

  ngOnInit() {
    // reset login status
    //this.authenticationService.logout();
    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  login() {
    this.spinner.show();
    this.httpService.login({ userName: this.model.username, password: this.model.password}).subscribe(response => {
      this.spinner.hide();
      this.responseMes = response.message;
      if(this.responseMes !== null && this.responseMes !== ""){
        this.toastService.show(response.message, { classname: 'bg-danger text-light', delay: 10000 });
      } else {
        this.toastService.show("Welcome "+response.data.firstName, { classname: 'bg-success text-light', delay: 10000 });
        localStorage.setItem("loginStatus", "true");
        localStorage.setItem("loginUser", response.data.userName);
        localStorage.setItem("loginUserId", response.data.userId);
        localStorage.setItem("token", response.token);
        this.router.navigate(["/"]);
      }
    },error => {
        this.alertService.error(error);
      });
  }

}
