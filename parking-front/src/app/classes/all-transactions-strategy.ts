import { ConsumptionTransaction } from "../models/consumption-transaction";
import { Transaction } from "../models/transaction";
import { TransactionsComponent } from "../transactions/transactions.component";
import { TransactionFilteringStrategy } from "./transaction-filtering-strategy";
import { ConsumptionOnlyStrategy } from "./consumption-only-strategy";
import { RechargeOnlyStrategy } from "./recharge-only-strategy";

export class AllTransactionsStrategy extends TransactionFilteringStrategy{

    constructor(transactionC: TransactionsComponent){
        super(transactionC);
    }
    
    public override filter(): Transaction[] {
        let allTransactions: Transaction[] = [];
        this.getTransactionComponent().transacciones?.forEach((transaction) => {
            allTransactions = ConsumptionOnlyStrategy.addConsumption(transaction, allTransactions);
            allTransactions = RechargeOnlyStrategy.addRecharge(transaction, allTransactions);
            console.log(transaction.date)
        })
        return this.sortByDate(allTransactions);
    }
}
