# Internship Management System for Universities

## Justification

This project is a web application that acts as a platform to manage external internships for degrees belonging to a university.

This application facilitates the internship management process and its bureaucracy by the administration of the faculty itself, as well as allowing better communication between student and tutor, with the latter being able to give feedback to their student for whatever they require.

## Setup

Install the following:
 - **JRE 1.8**
 - **Maven 3.5.4**
 - **MySQL 8.0.12**
 - **Tomcat 8.5**

The MySQL credentials of your DB must be changed in the following files:
 - `…/Gestion-Practicas/src/main/resources/spring/config/data.xml`
 - `…/Gestion-Practicas/src/main/java/utilities/SubirDocumentoDBServlet.java`
 - `…/Gestion-Practicas/src/main/java/utilities/DescargarDocumentoDBServlet.java`
 - `…/Gestion-Practicas/src/main/java/controllers/DocumentoController.java`

Execute the **“INSTALACION.SQL”** script attached in the **“SQL Scripts”** folder to create the database and its tables.

With this same script, a coordinator user will also be inserted with generic data to be able to use the system, with the following credentials:

 - **UVUS**: coordi
 - **Password**: coordi

The application is configured to be deployed in a “Gestion-Practicas” context path, which means that it will work correctly as long as the application URL has the following format:

`<host>/Gestion-Practicas/…`

The application can be deployed with Tomcat.
