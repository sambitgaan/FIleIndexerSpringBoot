import { Component, OnInit } from '@angular/core';
import { HttpgeneralService } from '../httpgeneral.service';
import { Router } from '@angular/router';

import { NgxSpinnerService } from "ngx-spinner";
import { ToastService } from '../toast-service';

@Component({
  selector: 'app-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.css']
})
export class DeleteComponent implements OnInit {

  deleteBtnEnabled = true;
  constructor(
    private router: Router,private spinner: NgxSpinnerService, private httpgeneralService: HttpgeneralService, public toastService: ToastService) { }

  ngOnInit(): void {
  }

  deleteFiles() {
    this.spinner.show();
    var userNum = localStorage.getItem("loginUserId");
    
    if(userNum){
    this.httpgeneralService.deleteSearchedFiles({ userId: userNum}).subscribe(response => {
        this.spinner.hide();
        this.deleteBtnEnabled = false;
        this.toastService.show('Files Removed Successfully         ', { classname: 'bg-success text-light', delay: 15000 });
      });  
    } else {
      this.toastService.show('Session Expired. Please Login ', { classname: 'bg-warning text-light', delay: 15000 });
      this.router.navigate(['/login']);
    }
  }
}
