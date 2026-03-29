# 🌐 API Gateway - ElectrodoStore

## 📌 Descripción
Punto de entrada único al ecosistema de microservicios de ElectrodoStore.
Todas las peticiones externas pasan por este servicio, que se encarga de
enrutarlas al microservicio correspondiente mediante **Spring Cloud Gateway**.

Forma parte de la arquitectura de microservicios basada en Spring Cloud.

---

## ⚙️ Tecnologías utilizadas

- Java + Spring Boot
- Spring Cloud Gateway
- Spring Cloud (Eureka Client)
- Load Balancer (`lb://`)

---

## 🗺️ Rutas configuradas

| Ruta externa | Microservicio destino | Puerto interno |
|---|---|---|
| `/api/producto/**` | `producto-service` | `8181` |
| `/api/cliente/**` | `cliente-service` | `8080` |
| `/api/carrito/**` | `carrito-service` | `8282` |
| `/api/venta/**` | `venta-service` | `9191` |

> El prefijo `/api/{servicio}` es eliminado antes de reenviar la petición
> al microservicio destino mediante el filtro `StripPrefix=2`.

---

## 🔁 Ejemplo de enrutamiento
```http
# Petición externa
GET http://localhost:9090/api/producto/productos

# Petición reenviada internamente
GET http://producto-service/productos
```

---

## ⚖️ Load Balancing

El gateway utiliza el prefijo `lb://` para resolver los microservicios
mediante **Eureka**, distribuyendo el tráfico entre instancias disponibles
de forma automática.

---

## 🌐 Registro en Eureka

El API Gateway se registra en Eureka Server como cualquier otro servicio,
lo que le permite descubrir dinámicamente las instancias de los
microservicios a los que enruta.

---

## 🔌 Configuración de red

| Propiedad | Valor |
|---|---|
| Puerto | `9090` |
| Acceso | ✅ Punto de entrada público |

---

## ▶️ Ejecución local

> ⚠️ Requiere que **Config Server** y **Eureka Server** estén corriendo
> antes de iniciar este servicio.

**Con Maven**
```bash
# Corre en el puerto 9090
mvn spring-boot:run
```

**Con Docker**
```bash
docker build -t api-gateway .
```

---

## 💡 Decisiones de diseño

- Enrutamiento dinámico mediante Eureka — sin IPs hardcodeadas
- `StripPrefix=2` para mantener URLs limpias en los microservicios internos
- Load Balancing integrado sin configuración adicional

---

## 🚀 Mejoras futuras

- Implementación de autenticación y autorización (JWT / OAuth2)
- Rate limiting por cliente
- Logging centralizado de peticiones entrantes