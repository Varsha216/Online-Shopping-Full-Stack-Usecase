import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthRequest } from '../Models/auth-request';
import { AuthResponse } from '../Models/auth-response';
import { AuthService } from '../Services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public message: string = "";
  public authRequest: AuthRequest = new AuthRequest();
  public authResponse: AuthResponse = new AuthResponse();

  constructor(
    private service: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    // this.message = "";
    this.service.logout();
  }

  public onSubmit(){
    this.service.authenticate(this.authRequest)
                          .subscribe({
                            next: (data) => {
                              this.service.setToken(data);
                              console.log(this.service.getToken()); 
                            },
                            error: (error) =>{
                              this.message = "Username/password is incorrect";
                            },
                            complete: () => this.validate(this.service.getToken())
                          });
    }

  public validate(token: any){
    this.service.validate(token)
                          .subscribe({
                            next: data =>{
                              this.service.setAuthResponse(data);
                              console.log(this.service.getAuthResponse());
                            },
                            error: (error) => this.service.logout(),
                            complete: () => this.router.navigate(['dashboard'])
                          });

  }

}
