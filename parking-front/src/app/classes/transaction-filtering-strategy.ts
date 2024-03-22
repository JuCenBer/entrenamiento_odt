import { Transaction } from "../models/transaction";
import { TransactionsComponent } from "../transactions/transactions.component";

export abstract class TransactionFilteringStrategy{

    private transactionComponent!: TransactionsComponent;

    constructor(transactionComponent: TransactionsComponent){
        this.transactionComponent = transactionComponent
    }

    public abstract filter():Transaction[];

    public getTransactionComponent(): TransactionsComponent{
        return this.transactionComponent;
    }
}

