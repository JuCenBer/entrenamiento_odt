export class User {
    cellphone: string;
    city: string;
    vehiculos: string[];
    token: string;
    permissions: string[];

    constructor(cellphone: string, city: string, vehiculos: string[], token: string, permissions: string[]){
        this.cellphone = cellphone;
        this.city = city;
        this.vehiculos = vehiculos;
        this.token = token;
        this.permissions = permissions;
    }
}
