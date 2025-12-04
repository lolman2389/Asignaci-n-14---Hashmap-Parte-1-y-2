package estructuras;

import java.util.LinkedList;

/**
 * Implementacion de tabla hash con encadenamiento separado.
 * Soporta tipos geneéricos y redimensionamiento dinámico.
 * * El factor de carga maximo es 0.75, tras lo cual se duplica
 * la capacidad automaticamente.
 * * @param <K> Tipo de las claves
 * @param <V> Tipo de los valores
 * @author [Marcelo Terminel Peralta]
 * @version 1.0
 */
public class TablaHash<K, V> implements Diccionario<K, V> {

    // Clase interna: Almacena un par clave-valor
    private class Nodo<K, V> {
        K key;
        V value;

        public Nodo(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // Atributos principales
    private LinkedList<Nodo<K, V>>[] tabla; // Arreglo de listas (buckets)
    private int size;                       // Cantidad de elementos almacenados (N)
    private int capacidad;                  // Tamaño del arreglo (M)
    private static final double FACTOR_CARGA_MAX = 0.75;

    /**
     * Constructor por defecto.
     * Inicializa la tabla con capacidad 11 (numero primo).
     */
    @SuppressWarnings("unchecked")
    public TablaHash() {
        this.capacidad = 11;
        this.tabla = new LinkedList[capacidad];
        this.size = 0;

        // Inicializar cada posicion del arreglo
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }
    }

    /**
     * Calcula el índice hash para una clave.
     * Maneja correctamente hashCodes negativos usando una mascara de bits.
     * * @param key La clave a procesar
     * @return Indice valido en [0, capacidad-1]
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacidad;
    }

    /**
     * Inserta un par clave-valor en la tabla hash.
     * Si la clave ya existe, actualiza su valor.
     * Verifica automaticamente el factor de carga y redimensiona si es necesario.
     * * Complejidad: O(1) promedio, O(n) si se requiere resize.
     * * @param key La clave unica del elemento (no puede ser null)
     * @param value El valor asociado a la clave
     * @throws NullPointerException si key es null
     */
    @Override
    public void put(K key, V value) {
        if (key == null) throw new NullPointerException("La clave no puede ser nula");

        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        // 3. Recorrer la lista buscando si key ya existe
        for (Nodo<K, V> nodo : lista) {
            if (nodo.key.equals(key)) {
                nodo.value = value; // Actualizar valor existente
                return;
            }
        }

        // Si no existe, crear nuevo nodo y agregarlo
        lista.add(new Nodo<>(key, value));
        size++;

        // 5. Verificar factor de carga
        if ((double) size / capacidad >= FACTOR_CARGA_MAX) {
            resize();
        }
    }

    /**
     * Recupera el valor asociado a una clave.
     * * Complejidad: O(1) promedio.
     * * @param key La clave a buscar
     * @return El valor asociado, o null si no existe
     */
    @Override
    public V get(K key) {
        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        for (Nodo<K, V> nodo : lista) {
            if (nodo.key.equals(key)) {
                return nodo.value;
            }
        }
        return null;
    }

    /**
     * Elimina un par clave-valor de la tabla.
     * * @param key La clave del elemento a eliminar
     * @return El valor eliminado, o null si la clave no existe
     */
    @Override
    public V remove(K key) {
        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        for (int i = 0; i < lista.size(); i++) {
            Nodo<K, V> nodo = lista.get(i);
            if (nodo.key.equals(key)) {
                V valorEliminado = nodo.value;
                lista.remove(i);
                size--;
                return valorEliminado;
            }
        }
        return null;
    }

    /**
     * Verifica si una clave existe en la tabla.
     * * @param key La clave a verificar
     * @return true si existe, false en caso contrario
     */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Retorna la cantidad de elementos almacenados.
     * * @return El tamaño actual de la tabla
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Redimensiona la tabla hash cuando el factor de carga supera el umbral.
     * Duplica la capacidad y reubica todos los elementos.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        LinkedList<Nodo<K, V>>[] tablaVieja = tabla;
        capacidad = capacidad * 2;
        tabla = new LinkedList[capacidad];

        // Inicializar cada posición del nuevo arreglo
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }

        // Reinsertar todos los elementos (rehashing)
        // size se mantiene igual, no es necesario resetearlo a 0 porque no llamamos a put()
        // pero reiniciamos la estructura.
        
        for (LinkedList<Nodo<K, V>> bucket : tablaVieja) {
            for (Nodo<K, V> nodo : bucket) {
                // Recalcular indice con la nueva capacidad
                int nuevoIndice = hash(nodo.key);
                tabla[nuevoIndice].add(nodo);
            }
        }
    }
}