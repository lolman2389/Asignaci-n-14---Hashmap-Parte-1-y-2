package test;

import estructuras.TablaHash;

public class TestTablaHash {
    public static void main(String[] args) {
        System.out.println(" Pruebas de TablaHash ");
        
        TablaHash<String, Integer> mapa = new TablaHash<>();
        
        // 1. Prueba de insercion
        System.out.println("Insertando valores...");
        mapa.put("manzana", 5);
        mapa.put("pera", 3);
        mapa.put("uva", 12);
        
        System.out.println("Tamaño actual: " + mapa.size()); // Debería ser 3
        
        // 2. Prueba de actualizacion
        System.out.println("Actualizando 'manzana' a 10...");
        mapa.put("manzana", 10);
        System.out.println("Valor de manzana: " + mapa.get("manzana")); // Debería ser 10
        
        // 3. Prueba de colisiones y Rehashing
        // La capacidad inicial es 11. El límite es 11 * 0.75 = 8.25 (8 elementos)
        System.out.println("\nLlenando tabla para forzar resize...");
        for(int i = 0; i < 10; i++) {
            mapa.put("clave" + i, i);
        }
        // Ahora deberiamos tener 3 iniciales + 10 nuevos = 13 elementos
        // La tabla deberia haberse duplicado a 22.
        
        System.out.println("Tamaño despues de insercion masiva: " + mapa.size());
        System.out.println("Valor de 'clave5': " + mapa.get("clave5"));
        
        // 4. Prueba de eliminacion
        System.out.println("\nEliminando 'pera'...");
        mapa.remove("pera");
        System.out.println("¿Existe 'pera'? " + mapa.containsKey("pera")); // false
    }
}