import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CustomValidator } from '../shared/custom.validator';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpgeneralService } from '../httpgeneral.service';
import { NgxSpinnerService } from "ngx-spinner";
import { ToastService } from '../toast-service';
import { Config } from '../models/config';

@Component({
  selector: 'app-config',
  templateUrl: './config.component.html',
  styleUrls: ['./config.component.css']
})
export class ConfigComponent implements OnInit {

  formName: any = FormGroup;
  config: Config | undefined;
  dirValue: String | undefined;
  removeLogPath: String | undefined;
  IndexDirPath: String | undefined;
  InputFileListPath: String | undefined;
  searchedFilesPathlog: String | undefined;
  userId: String | undefined;
  configId: String | undefined;

  responseData: Object | undefined;
  configObj: Object | undefined;

  constructor(private fb: FormBuilder, private httpService: HttpgeneralService, private spinner: NgxSpinnerService, public toastService: ToastService) { }

  //ng model values
  subDirVal: any = ""; subDirError: Boolean = false;

  ngOnInit() {
    this.formName = this.fb.group({
      dirInput: ['', [Validators.required]],
      delLogFileInput: ['', [Validators.required]],
      fileListInput: ['', [Validators.required]],
      subDirInput: ['', [Validators.required]],
      searchedFilesPathlog: ['', [Validators.required]]
    });

    this.spinner.show();
    this.responseData = this.httpService.getConfigDetails().subscribe(response => {
      this.spinner.hide();
      this.userId = response.userId;
      this.configId = response.configId;
      this.dirValue = response.dirPath;
      this.IndexDirPath = response.indexDirPath;
      this.removeLogPath = response.removedFilesLogPath;
      this.InputFileListPath = response.filesLogPath;
      this.searchedFilesPathlog = response.searchedFilesPathlog;
    });
  }

  submit(frmGrp: any) {
    this.subDirVal = frmGrp.value.subDirInput;
    console.log(frmGrp.value.dirInput + "   " + this.subDirVal);
    if ((frmGrp.value.dirInput && String(this.subDirVal).startsWith(frmGrp.value.dirInput) || frmGrp.value.dirInput === String(this.subDirVal))) {
      this.subDirError = true;
    } else {
      this.subDirError = false;
    }

    if (!this.subDirError) {
      this.spinner.show();
      this.httpService.saveConfig({ 'userId': localStorage.getItem("loginUserId"), "dirPath": this.dirValue, "indexDirPath": this.IndexDirPath, "removedFilesLogPath": this.removeLogPath, "filesLogPath": this.InputFileListPath, "searchedFilesPathlog" : this.searchedFilesPathlog } as Config).subscribe(response => {
        this.spinner.hide();
        this.toastService.show("Config saved successfully", { classname: 'bg-success text-light', delay: 10000 });
        console.log(response.dirPath);
      });
    }
  }



  //FOr FIle selection
  getDirPathdetials(event: any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
      console.log(event);
      console.log(event.target.value);
      reader.onload = (event: any) => {
        //this.dirPath = event.target.result;
      }
      reader.readAsDataURL(event.target.files[0]);
    }
    var path = (window.URL || window.webkitURL).createObjectURL(event.target.files[0]);
    console.log('path', path);
  }

}
