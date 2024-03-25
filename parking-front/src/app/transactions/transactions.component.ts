import { Component, OnInit } from '@angular/core';
import { Transaction } from '../models/transaction';
import { UserService } from '../services/user-service/user.service';
import { ErrorMessage } from '../models/error-message';
import { TransactionFilteringStrategy } from '../classes/transaction-filtering-strategy';
import { AllTransactionsStrategy } from '../classes/all-transactions-strategy';
import { NgFor, NgIf, DatePipe } from '@angular/common';
import { ConsumptionOnlyStrategy } from '../classes/consumption-only-strategy';
import { RechargeOnlyStrategy } from '../classes/recharge-only-strategy';


@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [NgFor, NgIf, DatePipe],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css'
})
export class TransactionsComponent{

  transacciones?: Transaction[];
  filteredTransactions?: Transaction[];
  filteringStrategy?: TransactionFilteringStrategy; 
  errorMsg: ErrorMessage = {
    message:"",
    status: 0
  }

  constructor(private userService: UserService){

  }
  
  ngOnInit(){
    this.getTransactions();
    this.filteringStrategy = new AllTransactionsStrategy(this)
    this.filteredTransactions = this.filteringStrategy.filter()
  }

  getTransactions(): void{
    this.userService.getTransactions().subscribe({
      error:(e)=>{
        this.errorMsg = e.error;
      },
      next: (data) =>{
        this.transacciones = data;
      }
    })
  }

  setTransactionFilteringStrategy(strategy: TransactionFilteringStrategy):void{
    this.filteringStrategy = strategy;
    this.filteredTransactions = this.filteringStrategy.filter();
  }

  setAllTransactionsStrategy():void{
    this.setTransactionFilteringStrategy(new AllTransactionsStrategy(this));
  }

  setConsumptionOnlyStrategy():void{
    this.setTransactionFilteringStrategy(new ConsumptionOnlyStrategy(this));
  }

  setRechargeOnlyStrategy():void{
    this.setTransactionFilteringStrategy(new RechargeOnlyStrategy(this));
  }
}
