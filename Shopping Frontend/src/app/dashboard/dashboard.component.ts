import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderOutput } from '../Models/order-output';
import { CustomerService } from '../Services/customer.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  orderOutput: any;


  constructor(
    private customerService: CustomerService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.customerService.getOrderList()
                              ?.subscribe({
                                next: data => {
                                  this.orderOutput = data;
                                  console.log(this.orderOutput);
                                },
                                error: (error) => console.log(error)
                              });
  }

  onRowClick(order: OrderOutput){
    this.customerService.setSelectedItems(order.selectedItems);
    this.router.navigate(['orderDetails']);
  }

  
}
