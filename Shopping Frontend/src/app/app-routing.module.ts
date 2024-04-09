import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { PlaceOrderComponent } from './place-order/place-order.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  {path : "", redirectTo : "login", pathMatch: "full"},
  {path : "login", component : LoginComponent},
  {path: "register", component : RegisterComponent},
  {path: "dashboard", component : DashboardComponent},
  {path: "orderDetails", component : OrderDetailsComponent},
  {path: "placeOrder", component : PlaceOrderComponent},
  {path : "**", component : LoginComponent}    //if nothing matches
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
