export class User {
    cellphone: string;
    city: string;
    vehiculos: string[];
    token: string;
    permissions: string[];
    balance: number;

    constructor(cellphone: string, city: string, vehiculos: string[], token: string, permissions: string[], balance: number){
        this.cellphone = cellphone;
        this.city = city;
        this.vehiculos = vehiculos;
        this.token = token;
        this.permissions = permissions;
        this.balance = balance;
    }
}
