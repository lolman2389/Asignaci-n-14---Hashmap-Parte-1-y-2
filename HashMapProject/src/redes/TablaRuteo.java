package redes;

import estructuras.TablaHash;
import java.util.ArrayList;
import java.util.List;

/**
 * Tabla de ruteo implementada con HashMap personalizado.
 * Maneja la lógica de inserción y búsqueda "Longest Prefix Match".
 */
public class TablaRuteo {
    
    // Usamos TU TablaHash creada en la parte 1
    private TablaHash<String, Ruta> tabla;
    
    // Lista auxiliar para poder recorrer las claves 
    private List<String> redesConocidas; 
    
    private int consultasExitosas;
    private int consultasFallidas;

    public TablaRuteo() {
        this.tabla = new TablaHash<>();
        this.redesConocidas = new ArrayList<>();
        this.consultasExitosas = 0;
        this.consultasFallidas = 0;
    }

    /**
     * Agrega o actualiza una ruta en la tabla.
     * Aplica logica de menor metrica 
     */
    public void agregarRuta(Ruta ruta) {
        String clave = ruta.getRedDestino();

        if (tabla.containsKey(clave)) {
            Ruta rutaExistente = tabla.get(clave);
            // Solo actualizar si la nueva ruta tiene MENOR metrica 
            if (ruta.getMetrica() < rutaExistente.getMetrica()) {
                tabla.put(clave, ruta);
                System.out.println("[UPDATE] Ruta mejorada: " + clave);
            }
        } else {
            tabla.put(clave, ruta);
            redesConocidas.add(clave); 
            System.out.println("[ADD] Nueva ruta: " + clave);
        }
    }

    /**
     * Elimina una ruta de la tabla
     */
    public boolean eliminarRuta(String redDestino) {
        if (tabla.containsKey(redDestino)) {
            tabla.remove(redDestino);
            redesConocidas.remove(redDestino);
            System.out.println("[DELETE] Ruta eliminada: " + redDestino);
            return true;
        }
        return false;
    }

    /**
     * Busca la ruta apropiada para una IP destino
     * Implementa busqueda por red con longest prefix match simplificado
     */
    public Ruta buscarRuta(String ipDestino) {
        // Ordenamos las redes de mayor a menor mascara 
        // Esto asegura que encontremos la ruta mas especifica (Longest Match)
        redesConocidas.sort((a, b) -> obtenerCIDR(b) - obtenerCIDR(a));

        for (String red : redesConocidas) {
            // Verificar si la IP encaja en esta red
            if (ipPerteneceARedSimplificado(ipDestino, red)) {
                consultasExitosas++;
                return tabla.get(red);
            }
        }

        // Si no encontramos nada especifico, buscar ruta por defecto
        if (tabla.containsKey("0.0.0.0/0")) {
            consultasExitosas++;
            return tabla.get("0.0.0.0/0");
        }

        consultasFallidas++;
        return null; // Paquete descartado (Drop)
    }

    public void imprimirEstadisticas() {
        System.out.println("=================================================");
        System.out.println("ESTADISTICAS DE LA TABLA DE RUTEO:");
        // Como TablaHash no tiene metodo size() publico en todas las versiones, se usa la lista auxiliar
        System.out.println("- Total de rutas activas: " + redesConocidas.size());
        System.out.println("- Consultas exitosas: " + consultasExitosas);
        System.out.println("- Consultas fallidas: " + consultasFallidas);
        System.out.println("=================================================");
    }

    // Metodos Privados de Ayuda

    // Obtiene el número del CIDR 
    private int obtenerCIDR(String red) {
        try {
            return Integer.parseInt(red.split("/")[1]);
        } catch (Exception e) { return 0; }
    }

    // Logica simplificada para ver si una IP es parte de una subred
    private boolean ipPerteneceARedSimplificado(String ip, String redCIDR) {
        if (redCIDR.equals("0.0.0.0/0")) return false; // La default se maneja aparte

        String[] partesRed = redCIDR.split("/");
        String redBase = partesRed[0];
        int cidr = Integer.parseInt(partesRed[1]);

        String[] octetosIP = ip.split("\\.");
        String[] octetosRed = redBase.split("\\.");

        // Comprobacion rápida según el prefijo 
        if (cidr >= 8 && !octetosIP[0].equals(octetosRed[0])) return false;
        if (cidr >= 16 && !octetosIP[1].equals(octetosRed[1])) return false;
        if (cidr >= 24 && !octetosIP[2].equals(octetosRed[2])) return false;
        
        return true;
    }
}