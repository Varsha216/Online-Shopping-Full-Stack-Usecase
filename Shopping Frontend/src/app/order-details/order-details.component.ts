import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Item } from '../Models/item';
import { CustomerService } from '../Services/customer.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  selectedItems: Item[] =[];

  constructor(
    private customerService: CustomerService
  ) { }

  ngOnInit(): void {
    this.selectedItems = this.customerService.getSelectedItems();
    console.log(this.selectedItems);
  }

}
