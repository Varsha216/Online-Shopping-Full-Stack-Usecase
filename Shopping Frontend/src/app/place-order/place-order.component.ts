import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { OrderInput } from '../Models/order-input';
import { CustomerService } from '../Services/customer.service';

@Component({
  selector: 'app-place-order',
  templateUrl: './place-order.component.html',
  styleUrls: ['./place-order.component.css']
})
export class PlaceOrderComponent implements OnInit {
  itemList: any;
  totalPrice: number = 0;
  orderInput: OrderInput = new OrderInput();
  itemsInCart: string[] =[];
  date: Date = new Date();

  cartStatus : Map<string, boolean> = new Map<string, boolean>();

  constructor(
    public datepipe: DatePipe,
    private customerService: CustomerService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.customerService.getItemList()
                                  .subscribe({
                                    next: (data)=>{
                                      this.itemList = data;
                                    },
                                    error: (error)=>console.log(error),
                                    complete: ()=>{
                                      for(let item of this.itemList){
                                        this.cartStatus.set(item, false);
                                      }
                                    }
                                  })
  }

  addToCart(item: string, price: number){
    this.itemsInCart.push(item);
    this.cartStatus.set(item, true);
    this.totalPrice+= price;
  }
  removeFromCart(item: string, price: number){
    const index :number = this.itemsInCart.indexOf(item);
    if(index !== -1)
      this.itemsInCart.splice(index, 1);

    this.cartStatus.set(item,false);
    this.totalPrice-= price;
  }

  placeOrder(){
    var userId= localStorage.getItem("userId");
    if(userId)
      this.orderInput.customerId = +userId;

    this.orderInput.itemsInCart = this.itemsInCart;

    let latestDate = this.datepipe.transform(this.date, 'dd-MM-yyyy');
    if(latestDate)
      this.orderInput.orderDate = latestDate;
      
    this.customerService.placeOrder(this.orderInput)
                                            .subscribe({
                                              next: (data)=>{
                                                console.log(data);
                                              },
                                              error: (error)=> console.log(error),
                                              complete: ()=>{
                                                alert("Order Placed Successfully!!!");
                                                this.router.navigate(['dashboard']);
                                              }
                                            });
  }

}
