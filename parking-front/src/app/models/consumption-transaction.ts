import { Transaction } from "./transaction";

export class ConsumptionTransaction implements Transaction{
    type: string;
    date: Date;
    amount: number;
    newBalance: number;
    operation: string;

    constructor(type:string, date: Date, amount: number, newBalance: number, operation: string){
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.newBalance = newBalance;
        this.operation = operation;
    }

    public getAmountColor(): string {
        return '$red-600';
    }
}
