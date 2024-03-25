export interface Transaction {
    type: string;
    date: Date;
    amount: number;
    newBalance: number;
    operation: string;

    getAmountColor():string;
}
