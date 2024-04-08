import { RechargeTransaction } from "../models/recharge-transaction";
import { Transaction } from "../models/transaction";
import { TransactionsComponent } from "../transactions/transactions.component";
import { TransactionFilteringStrategy } from "./transaction-filtering-strategy";

export class RechargeOnlyStrategy extends TransactionFilteringStrategy{

    constructor(transactionC: TransactionsComponent){
        super(transactionC)
    }

    public override filter(): Transaction[] {
        let transactionList: Transaction[] = [];
        this.getTransactionComponent().transacciones!.forEach((transaction) => {
            transactionList = RechargeOnlyStrategy.addRecharge(transaction, transactionList);
        })
        return this.sortByDate(transactionList);
    }

    public static addRecharge(transaction: RechargeTransaction, transactionList:Transaction[]): Transaction[]{
        if(transaction.type == "recharge"){
                let consumptionTransaction = new RechargeTransaction(transaction.type,transaction.date, transaction.amount, transaction.newBalance, transaction.operation)
                transactionList.push(consumptionTransaction)
            }
        return transactionList;
    }

    
}
