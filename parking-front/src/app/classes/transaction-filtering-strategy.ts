import { Transaction } from "../models/transaction";
import { TransactionsComponent } from "../transactions/transactions.component";

export abstract class TransactionFilteringStrategy{

    private transactionComponent!: TransactionsComponent;

    constructor(transactionComponent: TransactionsComponent){
        this.transactionComponent = transactionComponent
    }

    public abstract filter():Transaction[];

    public sortByDate(transactions: Transaction[]): Transaction[]{
        return transactions.sort((a,b) => this.compararFechasAscendente(a.date, b.date));
    }

    public getTransactionComponent(): TransactionsComponent{
        return this.transactionComponent;
    }

    public compararFechasAscendente(fechaA: Date, fechaB: Date) {
        if (fechaA > fechaB) {
        return -1;
        }
        if (fechaA < fechaB) {
        return 1;
        }
        return 0;
        
    }
}

