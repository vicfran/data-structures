package wardrobe;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class Wardrobe<K, V> implements ConcurrentMap<K, V> {

    private HashMap m;

    public Wardrobe() {
        m = new HashMap<K, Drawer<V>>();
    }


    public boolean isOpen(Object i) {
        if (i == null) return false;

        if (m.get(i) != null) return (((Drawer<V>) m.get(i)).isOpen());
        else return false;
    }

    public boolean open(Object i) {
        if (i == null) return false;

        if (m.get(i) != null) return ((Drawer<V>) m.get(i)).open();
        else return false;
    }

    public boolean close(Object i) {
        if (i == null) return false;

        if (m.get(i) != null) return ((Drawer<V>) m.get(i)).close();
        else return false;
    }

    @Override
    public V get(Object o) {
        if (o == null) return null;

        return (((Drawer<V>) m.get(o)).isOpen()) ? ((Drawer<V>) m.get(o)).getValue() : null;
    }

    @Override
    public V put(K key, V value) {
        if ((key == null) || (value == null)) return null; // Null values are not accepted

        if (m.containsKey(key)) {
            if (((Drawer<V>) m.get(key)).isOpen()) {
                return ((Drawer<V>) m.put(key, value)).getValue();
            } else {
                return null;
            }
        }

        Drawer<V> v = new Drawer(value);
        m.put(key, v);
        ((Drawer<V>) m.get(key)).close();

        return v.getValue();
    }

    @Override
    public V remove(Object key) {
        if (key == null) return null;

        if (m.containsKey(key)) {
            if (((Drawer<V>) m.get(key)).isOpen()) {
                return ((Drawer<V>) m.remove(key)).getValue();
            } else {
            }
        }

        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        if ((key == null) || (value == null)) return false;

        if ((m.containsKey(key)) && (((Drawer<V>) m.get(key)).isOpen()) && (m.get(key).equals(value))) {
            m.remove(key);
            return true;
        }

        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        if ((key == null) || (oldValue == null)) return false;

        if ((m.containsKey(key)) && (((Drawer<V>) m.get(key)).isOpen()) && ((m.get(key).equals(oldValue)))) {
            m.put(key, newValue);
            return true;
        }

        return false;
    }

    @Override
    public V replace(K key, V newValue) {
        if (key == null) return null;

        if ((m.containsKey(key)) && (((Drawer<V>) m.get(key)).isOpen())) {
            return ((Drawer<V>) m.put(key, newValue)).getValue();
        }

        return null;
    }

    @Override
    public int size() {
        return m.size();
    }

    @Override
    public boolean isEmpty() {
        return m.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null) return false;

        return m.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null) return false;

        return m.containsValue(value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        m.putAll(map);
    }

    @Override
    public void clear() {
        m.clear();
    }

    @Override
    public Set<K> keySet() {
        return m.keySet();
    }

    @Override
    public Collection<V> values() {
        return m.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return m.entrySet();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        if (key == null) return null;

        if ((m.containsKey(key)) && (m.get(key) == null)) {
            return ((Drawer<V>) m.put(key, value)).getValue();
        }

        return null;
    }
    private static class Drawer<V> {

        private V value;
        private boolean state;

        public Drawer() {
            state = true;
        }

        public Drawer(V value) {
            this.value = value;
            state = true;
        }

        public boolean close() {
            return state = false;
        }

        public boolean open() {
            return state = true;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public boolean isOpen() {
            return state;
        }

        @Override
        public boolean equals(Object another) {
            if (another == null) return false;

            if (((Drawer<V>)another).getValue() == this.value) {
                return true;
            }

            return false;
        }
    }
}