export class User {
    cellphone: string;
    city: string;
    vehiculos: string[];

    constructor(cellphone: string, city: string, vehiculos: string[]){
        this.cellphone = cellphone;
        this.city = city;
        this.vehiculos = vehiculos;
    }
}
