export interface Transaction {
    type: string;
    date: Date;
    amount: number;
    operation: string;

    getAmountColor():string;
}
