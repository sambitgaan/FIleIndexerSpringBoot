import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CustomValidator } from '../shared/custom.validator';


@Component({
  selector: 'app-config',
  templateUrl: './config.component.html',
  styleUrls: ['./config.component.css']
})
export class ConfigComponent implements OnInit {

  formName: any = FormGroup;

  constructor(private fb: FormBuilder) { }

  dirPath = ""; indexDirPath = ""; mergedDirPath = ""; listFilePath = ""; delLogFilePath = "";
  subDirVal : any = ""; subDirError: Boolean = false;

  ngOnInit() {
    this.formName = this.fb.group({
      dirInput: ['', [Validators.required]],
      delLogFileInput: ['', [Validators.required]],
      fileListInput: ['', [Validators.required]],
      mergedDirInput: ['', [Validators.required]],
      subDirInput: ['', [Validators.required]]
    });
  }

  getDirPathdetials(event: any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
      console.log(event);
      console.log(event.target.value);
      reader.onload = (event: any) => {
        this.dirPath = event.target.result;
      }
      reader.readAsDataURL(event.target.files[0]);
    }

    var path = (window.URL || window.webkitURL).createObjectURL(event.target.files[0]);
    console.log('path', path);

  }

  submit(frmGrp: any) {
    this.subDirVal = frmGrp.value.subDirInput;
    console.log(frmGrp.value.dirInput + "   " + this.subDirVal);
    if(frmGrp.value.dirInput &&  String(this.subDirVal).startsWith(frmGrp.value.dirInput)){
      this.subDirError = true;
    }
    console.log(frmGrp.value.numberInput);
  }

}
