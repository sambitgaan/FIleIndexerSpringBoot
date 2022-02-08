import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { slideInAnimation } from './animations';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
  animations: [slideInAnimation]
})
export class AppComponent {
  title = 'purging';

  ngOnInit(): void {
    localStorage.setItem("IndexStatus", "enable");
    localStorage.setItem("mergeStatus", "enable");
    localStorage.setItem("searchStatus", "enable");
    localStorage.setItem("removeStatus", "enable");
  }

  getAnimationData(outlet: RouterOutlet) {
    return outlet?.activatedRouteData?.['animation'];
  }
}
