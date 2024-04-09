import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CustomerProfile } from '../Models/customerProfile';
import { Item } from '../Models/item';
import { OrderInput } from '../Models/order-input';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  items : Item[] =[];
  isRegistering: boolean = false;
  isOrdering: boolean = false;

  constructor(
    private http: HttpClient
  ) { }

  doRegisteration(customerProfile: CustomerProfile){
    return this.http.post("http://localhost:9191/customer/save", customerProfile);
  }

  getOrderList() {
    var userId = localStorage.getItem("userId");

    if(userId){
      var id : number = +userId;
      return this.http.get(`http://localhost:9191/orders/customer/${id}`,
                {
                  headers: new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem("token")}`)
                });
    }
    return null;
  }

  getItemList() {
    return this.http.get("http://localhost:9191/items/",
                        {
                          headers: new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem("token")}`)
                        });
  }

  placeOrder(orderInput: OrderInput) {
    return this.http.post("http://localhost:9191/orders/save", orderInput,
                            {
                              headers: new HttpHeaders().set('Authorization',`Bearer ${localStorage.getItem("token")}`)
                            });
  }

  setSelectedItems(item: Item[]){
    this.items = item;
  }

  getSelectedItems() {
    return this.items;
  }

  setIsRegistering(status: boolean){
    this.isRegistering = status;
  }
  getIsRegistering(){
    return this.isRegistering;
  }

  setIsOrdering(status: boolean){
    this.isOrdering = status;
  }
  getIsOrdering(){
    return this.isOrdering;
  }
}
