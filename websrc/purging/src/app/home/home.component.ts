import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  showNavigationArrows = false;
  showNavigationIndicators = false;
  pauseOnHover = true;

  
  constructor(config: NgbCarouselConfig) { 
    config.interval = 1500;
    config.wrap = false;
    config.keyboard = false;
    config.showNavigationArrows = false;
    config.pauseOnHover = false;
    config.wrap = true;
  }

  ngOnInit(): void {
    
  }

}
