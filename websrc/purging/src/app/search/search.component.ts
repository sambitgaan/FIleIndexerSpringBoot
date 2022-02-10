import { Component, OnInit } from '@angular/core';
import { HttpgeneralService } from '../httpgeneral.service';


import { NgxSpinnerService } from "ngx-spinner";
import { ToastService } from '../toast-service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  searchingEnabled = true;
  constructor(private spinner: NgxSpinnerService, private httpgeneralService: HttpgeneralService, public toastService: ToastService) { }

  ngOnInit(): void {
  }

  searchIndex() {
    this.spinner.show();
    this.httpgeneralService.searchFile()
      .subscribe(response => {
        this.spinner.hide();
        this.searchingEnabled = false;
        this.toastService.show('Search completed Successfully         ', { classname: 'bg-success text-light', delay: 10000 });
      });  
  }

}
