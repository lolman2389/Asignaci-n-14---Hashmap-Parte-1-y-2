package estructuras;

/**
 * Interfaz básica para un diccionario (mapa).
 * @param <K> Tipo de la clave.
 * @param <V> Tipo del valor.
 */
public interface Diccionario<K, V> {
    void put(K key, V value);
    V get(K key);
    V remove(K key);
    boolean containsKey(K key);
    int size();
}