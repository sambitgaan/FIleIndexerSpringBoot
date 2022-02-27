import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpgeneralService } from '../httpgeneral.service';
import { AlertService } from '../service/alert.service';
import { ToastService } from '../toast-service';
import { NgxSpinnerService } from "ngx-spinner";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  ngOnInit(): void {
  }

  model: any = {};
  loading = false;
  responseMes: string = "";

  constructor(
    private router: Router,
    private httpService: HttpgeneralService,
    private alertService: AlertService,
    private spinner: NgxSpinnerService,
    public toastService: ToastService) { }

  register() {
    //this.loading = true;
    this.spinner.show();
    this.httpService.saveUser(this.model).subscribe(response => {
      this.spinner.hide();
      this.responseMes = response.message;
      if (this.responseMes !== null && this.responseMes !== "") {
        this.toastService.show(response.message, { classname: 'bg-danger text-light', delay: 10000 });
      } else {
        this.toastService.show(response.data.firstName + "Registered successfully", { classname: 'bg-success text-light', delay: 10000 });
        this.router.navigate(['/login']);
      }
    },
      error => {
        this.alertService.error(error);
        //this.loading = false;
      });
  }

}
