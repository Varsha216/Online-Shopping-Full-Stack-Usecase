import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerProfile } from '../Models/customerProfile';
import { CustomerService } from '../Services/customer.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  customerProfile: CustomerProfile = new CustomerProfile();
  message: string="";

  constructor(
    private customerService: CustomerService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  doRegisteration(){
    this.customerService.doRegisteration(this.customerProfile)
                              .subscribe({
                                next: (data) =>{
                                  console.log(data);
                                  this.router.navigate(['login']);
                                },
                                error: (error)=> this.message = error,
                                complete: ()=>{
                                  this.customerService.setIsRegistering(false);
                                  this.router.navigate(['login']);
                                }
                              });
  }
}
