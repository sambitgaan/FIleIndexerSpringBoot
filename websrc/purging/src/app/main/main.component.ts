import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

   loginUserName = "";
  constructor() {
  }

  ngOnInit(): void {
    if (localStorage.getItem("loginStatus") === "true") {
      this.loginUserName =  this.getUserData() || "";
    }
  }

  getUserData() {
    return localStorage.getItem("loginUser");
  }

}
