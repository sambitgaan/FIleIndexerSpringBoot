import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  showNavigationArrows = false;
  showNavigationIndicators = false;
  pauseOnHover = true;

  loginStatus = 'false';

  
  constructor(config: NgbCarouselConfig, private router: Router) { 
    config.interval = 1500;
    config.wrap = false;
    config.keyboard = false;
    config.showNavigationArrows = false;
    config.pauseOnHover = false;
    config.wrap = true;
  }

  ngOnInit(): void {
    if(localStorage.getItem("loginStatus") === "true"){
      this.loginStatus = 'true';}
  }

  userLogout() {
    localStorage.clear();
    this.router.navigate(["/login"]);
  }

}
