import { Component, OnInit } from '@angular/core';
import { HttpgeneralService } from '../httpgeneral.service';
import { NgxSpinnerService } from "ngx-spinner";
import { ToastService } from '../toast-service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  generateIndexEnable = true;
  singleFolderStatus = true;
  constructor(
    private router: Router, private spinner: NgxSpinnerService, private httpService: HttpgeneralService, public toastService: ToastService) { }

  ngOnInit(): void {
    if (localStorage.getItem("IndexStatus") === 'disable') {
      this.generateIndexEnable = false;
    }
  }

  startIndex() {
    var userNum = localStorage.getItem("loginUserId");
    if (userNum) {
      this.spinner.show();
      this.httpService.startIndex({userId: userNum}).subscribe(response => {
        if(response.message == "Success"){
          this.spinner.hide();
          localStorage.setItem("IndexStatus", "disable");
          this.generateIndexEnable = false;
          this.singleFolderStatus = false;
          this.toastService.show('Index Generated Successfully         ', { classname: 'bg-success text-light', delay: 15000 });
          this.toastService.show('Please Merge Indexes     ', { classname: 'bg-warning text-light', delay: 10000 });
        } else {
          this.spinner.hide();
          this.toastService.show(response.message, { classname: 'bg-warning text-light', delay: 10000 });
        }
      });
    } else {
      this.toastService.show('Session Expired. Please Login ', { classname: 'bg-warning text-light', delay: 15000 });
      this.router.navigate(['/login']);
    }
  }

  mergeInexesInSingleDir() {
    var userNum = localStorage.getItem("loginUserId");
    if (userNum) {
      this.spinner.show();
      this.singleFolderStatus = true;
      this.httpService.mergeIndexes({userId: userNum}).subscribe(response => {
        this.spinner.hide();
        this.toastService.show('Merged Successfully           ', { classname: 'bg-success text-light', delay: 15000 });
      },error => {
        console.log(error);
      });
    } else {
      this.toastService.show('Session Expired. Please Login ', { classname: 'bg-warning text-light', delay: 15000 });
      this.router.navigate(['/login']);
    }
  }

}
