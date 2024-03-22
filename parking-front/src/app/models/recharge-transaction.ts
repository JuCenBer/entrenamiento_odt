import { Transaction } from "./transaction";

export class RechargeTransaction implements Transaction{
    type: string;
    date: Date;
    amount: number;
    operation: string;

    constructor(type:string, date: Date, amount: number, operation: string){
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.operation = operation;
    }

    getAmountColor(): string {
        return 'text-success';
    }
}
