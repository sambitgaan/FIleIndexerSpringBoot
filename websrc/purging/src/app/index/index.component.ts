import { Component, OnInit } from '@angular/core';
import { HttpgeneralService } from '../httpgeneral.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  // startIndexing(){
  //   this.httpgeneralService.getRemove(null, 'data', { userId: 1, name: 'rowad', polo: 1 })
  //     .subscribe({
  //       next: () => {
  //         console.log("Success");
  //       },
  //       error: console.error
  //     });
  // }

}
