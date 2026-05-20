================================================================================
DEFENSA DE PROYECTO MICROSERVICIOS (AROMA)
================================================================================
Estudiante: Felipe Astudillo, Mauro Santiago
Fecha de Defensa: 19 de Mayo, 2026
Institución: Duoc UC
Github Link: https://github.com/maurosantiago23/fullstack.git
Modelo de Arquitectura: Inventario Centralizado (Bodega Central)
================================================================================

Esta guía contiene el flujo End-to-End (E2E) secuencial exacto para ejecutar en 
Postman durante la presentación frente a la comisión, utilizando los datos oficiales 
de prueba del equipo. Respeta las dependencias de la base de datos Oracle y valida 
la comunicación síncrona mediante Feign Clients.

--------------------------------------------------------------------------------
FASE 1: IDENTIDAD Y ACCESO (ms-usuarios | Puerto: 8081)
--------------------------------------------------------------------------------

1. POST - Registro de Usuario
   - URL: http://localhost:8081/api/v1/usuarios/registro
   - Body (raw -> JSON):
     {
         "nombreCompleto": "Estudiante Viña",
         "email": "estudiante.vina@duocuc.cl",
         "password": "password123",
         "rol": "CLIENTE"
     }
   - Acción en la demo: Explica que la contraseña se encripta con BCrypt antes 
     de guardarse en Oracle. (Asumiremos que genera el ID 3).

2. POST - Login de Usuario
   - URL: http://localhost:8081/api/v1/usuarios/login
   - Body (raw -> JSON):
     {
         "email": "estudiante.vina@duocuc.cl",
         "password": "password123"
     }
   - Acción en la demo: Muestra que el sistema autentica correctamente devolviendo 
     los datos del usuario (DTO).

3. GET - Listar Usuarios (Verificación 🔍)
   - URL: http://localhost:8081/api/v1/usuarios
   - Acción en la demo: Muestra a los profesores que el nuevo usuario aparece 
     formalmente registrado al final de la lista con su ID correspondientes.


--------------------------------------------------------------------------------
FASE 2: INVENTARIO GLOBAL (ms-catalogo | Puerto: 8082)
--------------------------------------------------------------------------------

4. POST - Crear Perfume Nuevo
   - URL: http://localhost:8082/api/v1/perfumes
   - Body (raw -> JSON):
     {
         "nombre": "CK EVERYONE EDT",
         "marca": "CALVIN KLEIN",
         "precio": 59990,
         "descripcion": "Fragancia limpia, verde, cítrica.",
         "stock": 10
     }
   - Acción en la demo: Creación dinámica en el catálogo central. (Asumiremos 
     que genera el ID 4).

5. GET - Listar Perfumes (Verificación 🔍)
   - URL: http://localhost:8082/api/v1/perfumes
   - Acción en la demo: Muestra que el perfume quedó guardado con un stock 
     inicial exacto de 10 unidades. Destaca que este ID 4 se usará para vender.


--------------------------------------------------------------------------------
FASE 3: INFRAESTRUCTURA, OFERTAS Y TRANSACCIÓN (ms-tiendas-ofertas | Puerto: 8083)
--------------------------------------------------------------------------------

6. POST - Crear Tienda Nueva
   - URL: http://localhost:8083/api/v1/tiendas
   - Body (raw -> JSON):
     {
       "nombre": "Mall Plaza El Sol",
       "ubicacion": "Quilpué"
     }
   - Acción en la demo: Demuestra que el CRUD de tiendas funciona de forma independiente. (Genera ID 4).

7. GET - Listar Tiendas (Verificación 🔍)
   - URL: http://localhost:8083/api/v1/tiendas

8. POST - Crear Oferta (Vincular Perfume a Tienda) 🏷️
   - URL: http://localhost:8083/api/v1/ofertas
   - Body (raw -> JSON):
     {
       "perfumeId": 4,
       "tiendaId": 4,
       "precioOferta": 49990
     }
   - Acción en la demo: Puente lógico donde asignan el perfume del catálogo (ID 4) 
     a la sucursal física (ID 4).

9. GET - Listar Ofertas (Verificación 🔍)
   - URL: http://localhost:8083/api/v1/ofertas

10. POST - Registrar Venta (El Momento de la Verdad 🚀)
    - URL: http://localhost:8083/api/v1/ventas
    - Body (raw -> JSON):
      {
          "usuarioId": 3,
          "perfumeId": 4,
          "total": 59990,
          "metodoPago": "Tarjeta Crédito"
      }
    - Explicación Técnica Obligatoria: 
      Al hacer clic en Send, VentaService gatilla de forma síncrona:
        a) usuarioFeignClient.validarUsuario(3) -> Consulta al puerto 8081.
        b) catalogoFeignClient.validarPerfume(4) -> Consulta al puerto 8082.
        c) catalogoFeignClient.descontarStock(4, 1) -> Resta stock en el puerto 8082.

11. GET - Verificar Descuento de Stock Directo (ms-catalogo 🔍)
    - URL: http://localhost:8082/api/v1/perfumes
    - Acción en la demo: ¡CLAVE! Vuelve a listar los perfumes del catálogo y 
      muestrale a los profesores que el stock de "CK EVERYONE EDT" bajó en vivo 
      de 10 a 9.

12. GET - Listar Historial de Ventas (ms-tiendas-ofertas 🔍)
    - URL: http://localhost:8083/api/v1/ventas


--------------------------------------------------------------------------------
FASE 4: EXPERIENCIA Y ANALÍTICA (ms-analisis-experiencia | Puerto: 8084)
--------------------------------------------------------------------------------

13. POST - Crear Reseña de Cliente
    - URL: http://localhost:8084/api/experiencia/resenas
    - Body (raw -> JSON):
      {
          "usuarioId": 3,
          "perfumeId": 4,
          "calificacion": 10,
          "comentario": "Flujo de prueba exitoso. Cuenta creada, login exitoso, perfume creado con éxito, stock descontado con éxito y reseña publicada con exito. Validaciones exitosas."
      }
    - Acción en la demo: Registra el feedback final del sistema.

14. GET - Consultar Reseñas (Verificación Final 🔍)
    - URL: http://localhost:8084/api/experiencia/resenas
    - Acción en la demo: Muestra la lista consolidada.
================================================================================
