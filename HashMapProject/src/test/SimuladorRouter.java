package test;

import redes.Ruta;
import redes.TablaRuteo;

public class SimuladorRouter {
    
    private TablaRuteo tablaRuteo;

    public SimuladorRouter() {
        this.tablaRuteo = new TablaRuteo();
        inicializarRutas();
    }

    // Carga las rutas iniciales 
    private void inicializarRutas() {
        // Default Gateway
        tablaRuteo.agregarRuta(new Ruta("0.0.0.0/0", "0.0.0.0", "200.100.50.1", "eth0", 10, "STATIC"));
        // Red Local
        tablaRuteo.agregarRuta(new Ruta("192.168.1.0/24", "255.255.255.0", "0.0.0.0", "eth1", 0, "CONNECTED"));
        // Red OSPF
        tablaRuteo.agregarRuta(new Ruta("10.0.0.0/8", "255.0.0.0", "192.168.1.254", "eth1", 20, "OSPF"));
        // Red DMZ
        tablaRuteo.agregarRuta(new Ruta("172.16.0.0/16", "255.255.0.0", "192.168.1.253", "eth2", 5, "STATIC"));
    }

    public void procesarPaquetes() {
        String[] paquetesEntrantes = {
            "192.168.1.50", // Debe ir a Red Local
            "10.5.3.2",     // Debe ir a OSPF
            "172.16.10.5",  // Debe ir a DMZ
            "8.8.8.8",      // Debe ir a Internet (Default)
            "192.168.2.1",  // No coincide local, va a Default
            "10.0.0.1"      // Debe ir a OSPF
        };

        System.out.println("=================================================");
        System.out.println("      SIMULADOR DE ROUTER - FORWARDING");
        System.out.println("=================================================\n");

        for (String ipDestino : paquetesEntrantes) {
            System.out.println("Paquete -> " + ipDestino);
            Ruta ruta = tablaRuteo.buscarRuta(ipDestino);

            if (ruta != null) {
                System.out.printf("\tRuta encontrada: %s%n", ruta.getRedDestino());
                System.out.printf("\tNext Hop: %s%n", ruta.getNextHop());
                System.out.printf("\tInterfaz salida: %s%n", ruta.getInterfaz());
                System.out.printf("\tProtocolo: %s (metrica: %d)%n", ruta.getProtocolo(), ruta.getMetrica());
                System.out.println("\tACCION: FORWARD\n");
            } else {
                System.out.println("\tACCION: DROP (No route to host)\n");
            }
        }
        
        tablaRuteo.imprimirEstadisticas();
    }

    public static void main(String[] args) {
        SimuladorRouter simulador = new SimuladorRouter();
        simulador.procesarPaquetes();
    }
}