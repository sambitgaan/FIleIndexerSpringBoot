import { NgModule, CUSTOM_ELEMENTS_SCHEMA  } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { IndexComponent } from './index/index.component';
import { SearchComponent } from './search/search.component';
import { HttpgeneralService } from './httpgeneral.service';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { ConfigComponent } from './config/config.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { BootstrapIconsModule } from 'ng-bootstrap-icons';
import { Alarm, App, Bookmark, Person, Twitter } from 'ng-bootstrap-icons/icons';
import { NgxSpinnerModule } from "ngx-spinner";

import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { DeleteComponent } from './delete/delete.component';
import { ToastsContainer } from './toasts-container.component';


const icons = {
  Alarm,
  App,
  Bookmark,
  Person,
  Twitter
};


@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    SearchComponent,
    HomeComponent,
    ProfileComponent,
    ConfigComponent,
    DeleteComponent,
    ToastsContainer
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    BootstrapIconsModule.pick(icons),
    BrowserAnimationsModule,
    NgxSpinnerModule
  ],
  providers: [HttpgeneralService],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule { }
