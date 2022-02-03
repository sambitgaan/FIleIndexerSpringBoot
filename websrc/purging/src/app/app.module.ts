import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { IndexComponent } from './index/index.component';
import { SearchComponent } from './search/search.component';
import { HttpgeneralService } from './httpgeneral.service';
 
@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [HttpgeneralService],
  bootstrap: [AppComponent]
})
export class AppModule { }
