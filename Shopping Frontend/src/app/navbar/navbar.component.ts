import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../Services/auth.service';
import { CustomerService } from '../Services/customer.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  userName: any;
  isLoggedIn : boolean = false;
  isRegistering : boolean = false;
  isOrdering: boolean = false;

  constructor(
    private authService: AuthService,
    private customerService: CustomerService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.userName = localStorage.getItem("userName");
    this.isLoggedIn = this.authService.isLoggedIn();
    this.isRegistering = this.customerService.getIsRegistering();
    this.isOrdering = this.customerService.getIsOrdering();
  }

  doLogout(){
    console.log("Logged out....");
    this.authService.logout();
  }

  doRegisteration() {
    this.customerService.setIsRegistering(true);
    this.router.navigate(['register']);
  }

  orderNow(){
    this.customerService.setIsOrdering(true);
    this.router.navigate(['placeOrder']);
  }
}
