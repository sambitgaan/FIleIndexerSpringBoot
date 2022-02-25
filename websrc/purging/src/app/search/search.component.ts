import { Component, OnInit } from '@angular/core';
import { HttpgeneralService } from '../httpgeneral.service';
import { Router } from '@angular/router';

import { NgxSpinnerService } from "ngx-spinner";
import { ToastService } from '../toast-service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  searchingEnabled = true;
  constructor(
    private router: Router,private spinner: NgxSpinnerService, private httpgeneralService: HttpgeneralService, public toastService: ToastService) { }

  ngOnInit(): void {
  }

  searchIndex() {
    this.spinner.show();
    var userNum = localStorage.getItem("loginUserId");
    if(userNum){
    this.httpgeneralService.searchFile({ userId : userNum}).subscribe(response => {
        this.spinner.hide();
        this.searchingEnabled = false;
        this.toastService.show('Search completed Successfully         ', { classname: 'bg-success text-light', delay: 10000 });
      }); 
    } else {
      this.toastService.show('Session Expired. Please Login ', { classname: 'bg-warning text-light', delay: 15000 });
      this.router.navigate(['/login']);
    } 
  }

}
