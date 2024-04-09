import { Item } from "./item";

export class OrderOutput {
    orderId: number =0;
    orderDescription: string ="";
    totalPrice: number=0;
    orderDate: Date = new Date();
    selectedItems: Item[] = [];
}