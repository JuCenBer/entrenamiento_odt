import { ConsumptionTransaction } from "../models/consumption-transaction";
import { Transaction } from "../models/transaction";
import { TransactionsComponent } from "../transactions/transactions.component";
import { TransactionFilteringStrategy } from "./transaction-filtering-strategy";

export class ConsumptionOnlyStrategy extends TransactionFilteringStrategy{

    constructor(transactionC: TransactionsComponent){
        super(transactionC)
    }

    public override filter(): Transaction[] {
        let transactionList: Transaction[] = [];
        this.getTransactionComponent().transacciones!.forEach((transaction) => {
            transactionList = ConsumptionOnlyStrategy.addConsumption(transaction, transactionList);
        })
        return this.sortByDate(transactionList);
    }

    public static addConsumption(transaction: ConsumptionTransaction, transactionList:Transaction[]): Transaction[]{
        if(transaction.type == "consumption"){
                let consumptionTransaction = new ConsumptionTransaction(transaction.type,transaction.date, transaction.amount, transaction.newBalance, transaction.operation)
                transactionList.push(consumptionTransaction)
            }
        return transactionList;
    }
}