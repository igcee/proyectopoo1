# EcoHydro-4: Módulo Hidropónico Automatizado

Primer Proyecto Programado de **IC-2101 Programación Orientada a Objetos**
Tecnológico de Costa Rica, Escuela de Computación, II Semestre 2026
Profesora: Ing. Ericka Solano Fernández

**Integrantes:** Ian Gabriel Castro Esquivel, Samuel Ureña Gonzalez, María Paula Monge Naranjo

## Descripción

Aplicación de consola en Java que simula el control de una unidad central de un invernadero hidropónico: ejecuta ciclos de muestreo con un brazo robótico, registra los reportes en una bitácora histórica, consulta el clima exterior y avisa por correo al operador cuando detecta una anomalía química crítica.

## Requisitos

- JDK 17 o superior
- Maven (las dependencias son `org.json`, `jakarta.mail-api` y `angus-mail`)
- Conexión a internet (para el clima y el correo)

## Cómo ejecutarlo

**Desde VS Code (recomendado):** instalar la extensión *Extension Pack for Java*, abrir la carpeta del proyecto, abrir `src/main/java/cr/ac/tec/ecohydro/Main.java` y pulsar **Run**.

## Estructura del proyecto

Arquitectura por capas:

- `cr.ac.tec.ecohydro.modelo`: `UCCI`, `BrazoMecanico`, `BitacoraHistorica`, `ReporteMuestreo`, `OperadorHumano`
- `cr.ac.tec.ecohydro.servicio`: `ServicioClima`, `ServicioCorreo`
- `cr.ac.tec.ecohydro.ui`: `Menu` (interfaz por consola con `Scanner`)
- `cr.ac.tec.ecohydro.Main`: punto de entrada

## Relaciones entre clases

No se usa herencia ni interfaces.

- **Composición:** `UCCI` crea su propio `BrazoMecanico` en el constructor. Si la unidad falla, el brazo deja de existir.
- **Agregación:** la `BitacoraHistorica` se asigna a la unidad desde afuera y es independiente de ella. Sus reportes se conservan aunque la unidad falle.
- **Asociación:** `UCCI` se asocia con un `OperadorHumano`.
- **Dependencia:** `UCCI` usa `ServicioClima` y `ServicioCorreo` recibiéndolos como parámetros, y `BrazoMecanico` crea objetos `ReporteMuestreo`.

El diagrama UML completo está en el entregable de la Fase 1.

## Menú principal

1. Configuración inicial (unidad, brazo y operador)
2. Ejecutar ciclo de muestreo
3. Sincronizar clima externo
4. Registrar y analizar en bitácora (envía correo si hay anomalía)
5. Ver estado de bitácora e historial
6. Simulación de falla crítica
7. Salir

Todas las entradas están validadas: si se escribe un dato inválido, se muestra un mensaje y se vuelve a preguntar.

## Reglas de negocio

- **Anomalía química crítica:** pH menor a 5.5 o mayor a 6.5, o conductividad menor a 1.2 o mayor a 2.4 dS/m.
- **Modo de contingencia:** si la potencia requerida supera el límite, o el brazo acumula más de 1000 horas, la unidad pasa a "Bajo Consumo / Mantenimiento Requerido" y la velocidad del brazo baja al 40%.
- **Gasto energético:** `(ángulo de extensión × 1.5) + (velocidad × 12.8)` W.
- **Compensación climática:** si la temperatura exterior supera los 32 °C, se suma 5% a la conductividad medida.

## Configuración de servicios externos

**Clima (RF4):** se usa la API pública de **Open-Meteo** (con su servicio de geocodificación). No necesita API key. La ubicación que se escribe en la configuración inicial debe ser el nombre de una ciudad.

**Correo (RF5):** se usa Jakarta Mail con SMTP sobre **Mailtrap** (sandbox de pruebas), por lo que los correos no llegan a una bandeja real, sino al buzón de pruebas de Mailtrap.

COMPLETAR  dónde se configuran las credenciales y cómo cambiarlas --- ian

## Pruebas

Las pruebas unitarias están en `src/test/java` y se ejecutan con:

mvn test

Cubren:

- Bitácora vacía: el porcentaje de lecturas anómalas es 0.
- Bitácora con un reporte anómalo y uno normal: el porcentaje es 50.
- Compensación climática: con la bandera activa, una conductividad de 2.0 pasa a 2.1; sin ella, se queda en 2.0.
- Umbral de 32 °C: por encima se activa la compensación; en 32 °C no.
- Consulta de clima fallida: se muestra un mensaje y no se cambia la compensación.

El clima de esas pruebas no llama a Open-Meteo. Usa datos fijos para no depender de la red.