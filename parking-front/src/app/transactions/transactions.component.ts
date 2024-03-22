import { Component } from '@angular/core';
import { Transaction } from '../models/transaction';
import { UserService } from '../services/user-service/user.service';
import { ErrorMessage } from '../models/error-message';
import { TransactionFilteringStrategy } from '../classes/transaction-filtering-strategy';
import { AllTransactionsStrategy } from '../classes/all-transactions-strategy';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css'
})
export class TransactionsComponent {

  transacciones?: Transaction[];
  filteredTransactions?: Transaction[];
  filteringStrategy?: TransactionFilteringStrategy; 
  errorMsg: ErrorMessage = {
    message:"",
    status: 0
  }

  constructor(private userService: UserService){

  }

  ngOnInit():void{
    this.setTransactionFilteringStrategy(new AllTransactionsStrategy(this));
    this.getTransactions();
  }

  getTransactions(): void{
    this.userService.getTransactions().subscribe({
      error:(e)=>{
        this.errorMsg = e.error;
      },
      next: (data) =>{
        this.transacciones = data;
        this.filteredTransactions = this.filteringStrategy?.filter();
      }
    })
  }

  setTransactionFilteringStrategy(strategy: TransactionFilteringStrategy):void{
    this.filteringStrategy = strategy;
  }
}
