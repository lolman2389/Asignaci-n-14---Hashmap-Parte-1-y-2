package redes;

/**
 * Representa una ruta en la tabla de ruteo
 * Contiene informacion sobre la red destino, gateway y metricas
 */
public class Ruta {
    private String redDestino;      // Ej: "192.168.1.0/24"
    private String mascaraSubred;   // Ej: "255.255.255.0"
    private String nextHop;         // IP del siguiente router
    private String interfaz;        // Interfaz de salida (eth0, eth1...)
    private int metrica;            // Costo de la ruta
    private String protocolo;       // STATIC, OSPF, BGP, CONNECTED

    public Ruta(String redDestino, String mascaraSubred, String nextHop, String interfaz, int metrica, String protocolo) {
        this.redDestino = redDestino;
        this.mascaraSubred = mascaraSubred;
        this.nextHop = nextHop;
        this.interfaz = interfaz;
        this.metrica = metrica;
        this.protocolo = protocolo;
    }

    // Getters
    public String getRedDestino() { return redDestino; }
    public String getMascaraSubred() { return mascaraSubred; }
    public String getNextHop() { return nextHop; }
    public String getInterfaz() { return interfaz; }
    public int getMetrica() { return metrica; }
    public String getProtocolo() { return protocolo; }

    @Override
    public String toString() {
        return String.format("Dest: %s, NextHop: %s, Int: %s, Proto: %s (%d)", 
            redDestino, nextHop, interfaz, protocolo, metrica);
    }
}