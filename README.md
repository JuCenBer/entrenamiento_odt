# Parking Application
Ejemplo de como authorizar un usuario usando JWT con Java y Angular

## Clonar el repositorio

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

Importar `parking_back.war` en el IDE (IDEA, Eclipse, etc) y levantar el proyecto usando Tomcat. 
> Desde el frontend se asume que el server esta escuchando en http://localhost:8080/parking-back

## Base de datos
Como BDC se utiliza Postgre. Las credenciales para conectarse a la base de datos se encuentran dentro del archivo __application.properties__ dentro de __parking_back__.
De ser necesario, las credenciales son las siguientes:
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

Por defecto, todas las ciudades son instanciadas con periodos de estacionamiento de 15 minutos.
Tener en cuenta que los servicios van a buscar la ciudad llamada "La Plata", por lo que la modificacion de esto lleve a fallos.
#### Feriados
Se encuentra programado en la clase `CityService` el metodo `checkExistingHolidays` que verifica la existencia de dias feriados. En caso de no existir ninguno crea los siguientes feriados:
* Navidad: 25/12/2024
* Año nuevo: 1/1/2025
* Dia de la Independencia Argentina: 9/7/2024
* Dia del Trabajador: 1/5/2024
  
Antes de iniciar el servidor, se pueden agregar más feriados a gusto, o se pueden agregar de forma manual en la base de datos. 
>Recordar que no se popularán los feriados mencionados en la base de datos si existe por lo menos uno
#### Vendedor
En la clase `SellerService` se encuentra el metodo `checkExistingSeller` que verifica la existencia de al menos un vendedor. En caso de que no exista ninguno, crea uno con las siguientes credenciales para iniciar sesion:
* username (o número de celular): vendedor
* password: 12345
  
>Tener en cuenta que no hay un metodo en la aplicacion para crear otro vendedor, por lo que si se quiere agregar uno, se deberá hacerlo de forma manual en la base de datos, asignandole el rol correspondiente.
#### Roles
En la clase `RoleService` se encuentra el metodo `checkExistingRoles` que verifica la existencia de los roles. En caso de que la cantidad sea distinta de dos (La aplicacion solo usa los roles "Automovilista" y "Vendedor"), procede a crearlos con sus respectivos permisos (que tambien los almacena en la base de datos). Estos luegos se utilizaran para la creacion del vendedor (que tendra el rol de "Vendedor") y el registro de usuarios (que tendran el rol de "Automovilista").

## Registro de usuarios
El registro de usuarios se puede hacer mediante el formulario de registro que se encuentra al inicio de la aplicacion. Estos se crearán con el rol de "Automovilista", sin vehiculos y con saldo cero.
Para agregarles saldo:
* Se puede hacer de forma manual en la base de datos, modificando la cuenta bancaria correspondiente al automovilista;
* O se puede utilizar la pantalla de carga de saldo del usuario vendedor creado por defecto.

## Consideraciones finales
Intenté crear esta solución haciendola lo más generica posible, y aplicando la mayor cantidad de patrones de diseño que se me ocurrieron. A medida que iba terminando el proyecto, se me iban ocurriendo otras formas de poder haber hecho algunas cosas, aplicando más patrones, o hacer otras cosas aún más genericas, que considero que podrían haber sido mejor elección. 
Algunas de estas consideraciones son:
- Considerar mejores formas de manejar el parking, quiza no está tan bueno andar creando y eliminando filas en la base de datos todo el tiempo.
    - Considerar usar patron State con la clase `Parking`.
- Utilizar un componente por cada tarjeta de la lista del historial de transacciones, con el objetivo de simplificar el html del componente `Transactions`
- Podria replantear los formularios para iniciar o finalizar estacionamiento como dos componentes distintos que se vayan alternando de acuerdo a si el usuario está estacionado o no.
- Implementar una pagina para un usuario que no tiene rol en caso de ser necesario, para que sea redirigido allí.
- Quiza podría haber utilizado el patrón Builder para armar la barra de navegación de acuerdo a los permisos que tiene el usuario.