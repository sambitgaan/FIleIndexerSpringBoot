import { Component, OnInit } from '@angular/core';
import { HttpgeneralService } from '../httpgeneral.service';


import { NgxSpinnerService } from "ngx-spinner";
import { ToastService } from '../toast-service';



@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  generateIndexEnable = true;
  singleFolderStatus = true;
  constructor(private spinner: NgxSpinnerService, private httpgeneralService: HttpgeneralService, public toastService: ToastService) { }

  ngOnInit(): void {
    if (localStorage.getItem("IndexStatus") === 'disable') {
      this.generateIndexEnable = false;
    }
  }

  startIndex() {
    this.spinner.show();
    this.httpgeneralService.startIndex()
      .subscribe(response => {
        this.spinner.hide();
        localStorage.setItem("IndexStatus", "disable");
        this.generateIndexEnable = false;
        this.singleFolderStatus = false;
        this.toastService.show('Index Generated Successfully         ', { classname: 'bg-success text-light', delay: 15000 });
        this.toastService.show('Please Merge Indexes     ', { classname: 'bg-warning text-light', delay: 10000 });
      });  
  }

  mergeInexesInSingleDir() {
    this.spinner.show();
    this.singleFolderStatus = true;
    this.httpgeneralService.mergeIndexes()
      .subscribe(response => {
        this.spinner.hide();
        this.toastService.show('Merged Successfully           ', { classname: 'bg-success text-light', delay: 15000 });
      });
  }

}
