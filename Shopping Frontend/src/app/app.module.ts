import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthService } from './Services/auth.service';
import { CustomerService } from './Services/customer.service';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthRequest } from './Models/auth-request';
import { TokenInterceptorService } from './Services/token-interceptor.service';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { RegisterComponent } from './register/register.component';
import { PlaceOrderComponent } from './place-order/place-order.component';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    DashboardComponent,
    OrderDetailsComponent,
    RegisterComponent,
    PlaceOrderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    AuthService,
    CustomerService,
    DatePipe,
    TokenInterceptorService,
    // {
    //   provide : HTTP_INTERCEPTORS,
    //   useClass : TokenInterceptorService,
    //   multi : true
    // }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
