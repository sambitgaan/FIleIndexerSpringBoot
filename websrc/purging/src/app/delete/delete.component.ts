import { Component, OnInit } from '@angular/core';
import { HttpgeneralService } from '../httpgeneral.service';


import { NgxSpinnerService } from "ngx-spinner";
import { ToastService } from '../toast-service';

@Component({
  selector: 'app-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.css']
})
export class DeleteComponent implements OnInit {

  deleteBtnEnabled = true;
  constructor(private spinner: NgxSpinnerService, private httpgeneralService: HttpgeneralService, public toastService: ToastService) { }

  ngOnInit(): void {
  }

  deleteFiles() {
    this.spinner.show();
    this.httpgeneralService.deleteSearchedFiles()
      .subscribe(response => {
        this.spinner.hide();
        this.deleteBtnEnabled = false;
        this.toastService.show('Files Removed Successfully         ', { classname: 'bg-success text-light', delay: 10000 });
      });  
  }
}
