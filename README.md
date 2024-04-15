# Parking Application
Ejemplo de como authorizar un usuario usando JWT con Java y Angular

## Clonar el repo

```shell 
git clone git@github.com:JuCenBer/entrenamiento_odt.git
```

> Alternativamente, se pueden descargar el codigo fuente sin usar git desde [aca](https://github.com/JuCenBer/entrenamiento_odt/archive/refs/heads/main.zip)
## Levantar el Cliente
El cliente esta construido usando Angular 17. 

1. Ingresar a la carpeta __parking_front__  
    ```shell
    cd parking_front
    ```
2. Instalar las dependencias  
    ```shell 
    npm i
    ```
3. Usar el __cli__ de angular para levantar el frontend en la terminal
    ```shell 
    ./node_modules/.bin/ng serve --open
    ```
    O tambien se puede utilizar
    ```
    ng serve
    ```
    > Se puede instalar el cli de angular usando el comando:   
    ```npm install -g @angular/cli ```

## Levantar el Server
Para poder hacer uso del frontend, es necesario que el server este levantado

Importar el proyecto dentro del folder __parking_back__ en el IDE (IDEA, Eclipse, etc) y levantar el proyecto usando Tomcat o Jetty. 
> Desde el frontend se asume que el server esta escuchando en http://localhost:8080/parking-back

## Base de datos
Como BDC se utiliza Postgre. Las credenciales para conectarse a la base de datos se encuentran dentro del archivo __application.properties__ dentro de __parking_back__.
Alternativamente, de ser necesario, las credenciales son las siguientes:
* Url: //localhost:5432/induccion_estacionamiento
* Username: postgres
* Password: postgres

Por defecto, la conexion a la misma se encuentra en el modo ```update``` 

### Populación de la base datos
La información elemental para el funcionamiento de la aplicacion se encuentra programada en el back para que esta sea populada de ser necesario
#### Ciudad
Se encuentra programado en la clase `CityService` el metodo `checkExistingCity` que se encarga de verificar la existencia de la ciudad sobre la que trabaja la aplicacion. En caso de que no exista o de que haya más de una ciudad, la crea con la siguiente información:
* Nombre: La Plata
* Horario de inicio de jornada: 8
* Horario de fin de jornada: 20
* Precio del periodo de estacionamiento: 2.5

Por defecto, todas las ciudades son creadas con periodos de estacionamiento de 15 minutos.
#### Feriados
Se encuentra programado en la clase `CityService` el metodo `checkExistingHolidays` que verifica la existencia de dias feriados. En caso de no existir ninguno crea los siguientes feriados:
* Navidad: 25/12/2024
* Año nuevo: 1/1/2025
* Dia de la Independencia Argentina: 9/7/2024
* Dia del Trabajador: 1/5/2024
  
Antes de iniciar el servidor, se pueden agregar más feriados a gusto. Recordar que no se popularán los feriados mencionados en la base de datos si existe por lo menos uno
#### Vendedor
