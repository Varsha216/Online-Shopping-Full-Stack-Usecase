import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthRequest } from '../Models/auth-request';
import { AuthResponse } from '../Models/auth-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public authResponse: any;

  constructor(
    private http : HttpClient,
    private router: Router
    ) { }

  welcome(){
    return this.http.get("http://localhost:9191/auth/welcome", {
                                                            responseType : 'text'
                                                          });
  }

  authenticate(authRequest : AuthRequest){
    // make rest call [POST] to /authenticate with authRequest as Request-body
    return this.http.post("http://localhost:9191/auth/authenticate", authRequest, 
                          { responseType : 'text',
                            //observe : 'response'
                          });
  }

  validate(securityToken : string){
    return this.http.get<AuthResponse>("http://localhost:9191/auth/validate",
                            {
                              headers: new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
                            });
  }

  setToken(jwt: string){
    localStorage.setItem("token", jwt);
    return true;
  }

  getToken(){
    return localStorage.getItem("token");
  }

  isLoggedIn():boolean {
    let token = localStorage.getItem("token");
    if(token==undefined || token==null || token==='')
      return false;
    else 
      return true;
  }

  logout(){
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    localStorage.removeItem("userName");
    this.authResponse = null;
    this.router.navigate(['login']);
    return true;
  }

  setAuthResponse(authResponse: AuthResponse){
    localStorage.setItem("userId", authResponse.id+"");
    localStorage.setItem("userName", authResponse.firstName);
    this.authResponse = authResponse;
  }

  getAuthResponse(): AuthResponse{
    return this.authResponse;
  }
}
