# ShieldLinkManager1

## Descripción
ShieldLinkManager1 es una aplicación desarrollada en Java con el objetivo de gestionar usuarios, préstamos, herramientas y reportes en un sistema organizado. Este proyecto utiliza **Java Swing** para las interfaces gráficas y **Maven** para la gestión de dependencias y compilación.

## Características principales
- **Gestión de usuarios:** Crear, modificar y eliminar usuarios con roles específicos (administrador, estudiante, profesor, técnico).
- **Gestión de herramientas:** Administrar herramientas con detalles como estado, tipo y cantidad disponible.
- **Préstamos:** Solicitar y gestionar préstamos de herramientas con seguimiento de su estado.
- **Reportes:** Generar reportes en formato PDF.
- **Inicio de sesión seguro:** Módulo de autenticación con cifrado SHA-256 para contraseñas.
- **Interfaz gráfica:** Diseño interactivo desarrollado con **Java Swing**.

## Estructura del proyecto
El proyecto está organizado en paquetes según su funcionalidad:

1. **`modelo`:** Clases que representan los datos del sistema.
   - Usuario, Herramienta, Prestamo, Reporte, etc.
2. **`vista`:** Interfaces gráficas de usuario.
   - Ventanas para gestionar usuarios, herramientas, préstamos y reportes.
3. **`controlador`:** Lógica de control para manejar eventos y lógica de negocio.
4. **`util`:** Clases utilitarias como encriptación, validaciones y conversión de fechas.

## Instalación

1. **Navega al directorio del proyecto**:
   ```bash
   cd ShieldLinkManager1
   ```

2. **Compila el proyecto con Maven**:
   ```bash
   mvn clean install
   ```

3. **Ejecuta el archivo `.jar` generado**:
   ```bash
   java -jar target/ShieldLinkManager1.jar
   ```

## Uso

1. **Inicio de sesión**:
   - Introduce tu usuario y contraseña.
   - Selecciona el rol correspondiente:
     - Administrador
     - Estudiante
     - Profesor
     - Técnico

2. **Gestión de recursos**:
   - Desde el menú principal, accede a las opciones para gestionar:
     - Usuarios
     - Herramientas
     - Préstamos
     - Reportes

3. **Generación de reportes**:
   - Ve a la sección de reportes.
   - Selecciona la opción para descargar un archivo en formato PDF.

## Dependencias

El archivo `pom.xml` incluye las siguientes dependencias principales:

- **Swing:** Para el diseño de interfaces gráficas.
- **Log4j:** Para el registro de actividades y errores.
- **MySQL Connector:** Para la conexión con bases de datos MySQL.

## Contribuciones

1. Realiza un fork del repositorio.
2. Crea una rama para tu contribución:
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. Realiza tus cambios y envía un Pull Request.

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## Contacto

Desarrollado por el equipo **CyberSentinels**. Para consultas, puedes enviar un correo a:  
📧 soporte@cybersentinels.com
