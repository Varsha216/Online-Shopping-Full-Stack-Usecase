import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor{

  constructor(
    private authService : AuthService
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler){
    let authToken = `Bearer ${localStorage.getItem('token')}`;
    if(authToken){
        req = req.clone({
        setHeaders : {  // creating the authorization header
          ['Authorization'] : authToken
        }
      });
    }
    return next.handle(req);
  }
}