[README.md](https://github.com/user-attachments/files/23953518/README.md)
# Proyecto: Implementación de HashMap y Simulador de Router

**Asignatura:** Estructura de Datos - Unidad 
**Autor:** [Marcelo Terminel Peralta]  
**ID:** [00000267339]

## Descripción del Proyecto
Este proyecto consta de dos partes fundamentales:
1.  **Parte 1:** Implementación desde cero de una estructura de datos `TablaHash` (HashMap) utilizando **Encadenamiento Separado** (Separate Chaining) para el manejo de colisiones y redimensionamiento dinámico.
2.  **Parte 2:** Aplicación práctica de la estructura anterior para crear una **Tabla de Ruteo** de red, capaz de simular decisiones de reenvío de paquetes (Forwarding) basándose en métricas y coincidencias de prefijo (Longest Prefix Match).

---

## Estructura del Proyecto
El código fuente se encuentra en la carpeta `src/` organizado en los siguientes paquetes:

### `src/estructuras/` (Core)
* **Diccionario.java**: Interfaz que define las operaciones básicas del mapa (put, get, remove).
* **TablaHash.java**: Implementación principal de la tabla hash con soporte para tipos genéricos `<K, V>`. Incluye lógica de *rehashing* automático cuando el factor de carga supera 0.75.

### `src/redes/` (Aplicación de Ruteo)
* **Ruta.java**: Modelo de datos que representa una entrada en la tabla de ruteo (Red destino, Máscara, NextHop, Interfaz, Métrica, Protocolo).
* **TablaRuteo.java**: Clase administradora que utiliza la `TablaHash` para almacenar rutas. Implementa la lógica de búsqueda "Longest Prefix Match" para determinar la mejor ruta de salida.

### `src/test/` (Pruebas)
* **TestTablaHash.java**: Pruebas unitarias para validar inserción, colisiones y redimensionamiento del HashMap.
* **SimuladorRouter.java**: Programa principal que inicializa un escenario de red empresarial y simula el tráfico de paquetes.

---

## Instrucciones de Compilación
Desde la raíz del proyecto (donde está este archivo README), abre una terminal y ejecuta:

```bash
javac src/**/*.java
