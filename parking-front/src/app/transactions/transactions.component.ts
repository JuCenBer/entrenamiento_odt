import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Transaction } from '../models/transaction';
import { UserService } from '../services/user-service/user.service';
import { ErrorMessage } from '../models/error-message';
import { TransactionFilteringStrategy } from '../classes/transaction-filtering-strategy';
import { AllTransactionsStrategy } from '../classes/all-transactions-strategy';
import { NgFor, NgIf, DatePipe } from '@angular/common';
import { ConsumptionOnlyStrategy } from '../classes/consumption-only-strategy';
import { RechargeOnlyStrategy } from '../classes/recharge-only-strategy';
import { ConfigService } from '../services/config-service/config.service';


@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [NgFor, NgIf, DatePipe, CommonModule],
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
  currency!: String;
  format!: string;

  constructor(private userService: UserService, private config: ConfigService){
    this.config.loadConfig().subscribe({
      next: (config)=>{
        this.currency = config.currencySymbol ?? this.config.getCurrencySymbol();
        let decimalPlaces = config.decimalPlaces;
        this.format = `1.${decimalPlaces}-${decimalPlaces}`;
      }
    })
  }
  
  ngOnInit(){
    this.getTransactions();
  }

  getTransactions(): void{
    this.userService.getTransactions().subscribe({
      error:(e)=>{
        this.errorMsg = e.error;
      },
      next: (data) =>{
        this.transacciones = data;
        this.setTransactionFilteringStrategy(new AllTransactionsStrategy(this))
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
