package assign09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A HashTable object
 * @authors Camden Lyon, Ibrahim Alasady, credit for isPrime method to GeeksForGeeks.org
 * @version 2026-11-11
 * @param <K>
 * @param <V>
 */
public class HashTable<K, V> implements Map<K, V>{

    private Object[] table;
    private final double maxLoadFactor;
    private int size = 0;
    private int occupied = 0;
    private static final Object DELETED = new Object();

    /**
     * Constructor with the default maximum load factor 0.5
     */
    public HashTable() {
    	this(0.5);
    }

    /**
     * Constructor with custom maximum load factor
     * @param maxLoadFactor, a double between 0 and 1, the load factor at which to double the array size
     * @throws IllegalArgumentException if parameter is out of range
     */
    public HashTable(double maxLoadFactor) {
    	if (maxLoadFactor <= 0.0 || maxLoadFactor >= 1.0) {
    		throw new IllegalArgumentException("load factor must be between 0 and 1");
    	}
    	this.maxLoadFactor = maxLoadFactor;
    	this.table = new Object[101];
    }
    
    /**
     * returns capacity of the current table
     * @return, an int, the current table capacity
     */
    public int capacity (){
    	return this.table.length;
    }
    
	@Override
	public void clear() {
		Arrays.fill(table, null);
		this.size = 0;
		this.occupied = 0;
	}

	@Override
	public boolean containsKey(K key) {
		return findActiveIndex(key) != -1;
	}

	@Override
	public boolean containsValue(V value) {
		for (int i = 0; i < this.table.length; i++) {
			if (this.table[i] != null && this.table[i] != DELETED) {
				MapEntry<K, V> entry = entryAt(i);
				if (Objects.equals(entry.getValue(), value)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<MapEntry<K, V>> entries() {
		List<MapEntry<K, V>> list = new ArrayList<>(size);
		for (int i = 0; i < table.length; i++) {
        	if (table[i] != null && table[i] != DELETED) {
        		MapEntry<K, V> entry = entryAt(i);
        		list.add(new MapEntry<>(entry.getKey(), entry.getValue()));
        	}
		}
		return list;
	}

	@Override
	public V get(K key) {
		int index = findActiveIndex(key);
		if (index != -1) {
			return entryAt(index).getValue();
		} else {
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public V put(K key, V value) {
		int existingIndex = findActiveIndex(key);
		if (existingIndex != -1) {
			MapEntry<K, V> entry = entryAt(existingIndex);
			V oldValue = entry.getValue();
			entry.setValue(value);
			return oldValue;
		}
		if ((double) (occupied + 1) / table.length > maxLoadFactor) {
			rehash();
		}
		int hash = hash(key);
		int firstDeletedIndex = -1;
		int i = 0;
		while (true) {
			int index = (int) (((long) hash + (long) i * i) % table.length);
			Object slot = table[index];
			if (slot == null) {
				int targetIndex;
				if (firstDeletedIndex != -1)
					targetIndex = firstDeletedIndex;
				else
					targetIndex = index;
				table[targetIndex] = new MapEntry<>(key, value);
				size++;
				if (firstDeletedIndex == -1) {
					occupied++;
				}
				return null;
			}
			if (slot == DELETED) {
				if (firstDeletedIndex == -1) {
					firstDeletedIndex = index;
				}
			}
			i++;
        }
	}

	@Override
	public V remove(K key) {
		int index = findActiveIndex(key);
		if (index != -1) {
        	V oldValue = entryAt(index).getValue();
            table[index] = DELETED;
            size--;
            return oldValue;
        }
		return null;
	}

	@Override
	public int size() {
		return this.size;
	}
	
    
    /**
     * Private helper method to computes the index
     */
    private int hash(K key) {
    	int code = key.hashCode();
    	if (code == Integer.MIN_VALUE) {
    		code = 0;
    	} else {
    		code = Math.abs(code);
    	}
    	return code % table.length;
    }
    
    /**
     * Probes the table using quadratic probing to locate an active key's index.
     */
	private int findActiveIndex(K key) {
		int hash = hash(key);
		int i = 0;
		while (i < table.length) {
			int index = (int) (((long) hash + (long) i * i) % table.length);
			Object slot = table[index];
			if (slot == null) {
				return -1;
			}
			if (slot != DELETED) {
				MapEntry<K, V> entry = entryAt(index);
				if (Objects.equals(entry.getKey(), key)) {
					return index;
				}
			}
			i++;
		}
		return -1;
    }
	
	/**
     * Resizes the array to the next prime >= 2 * current capacity and rehashes.
     */
	@SuppressWarnings("unchecked")
	private void rehash() {
		Object[] oldTable = table;
		int newCapacity = nextPrime(table.length * 2);
		table = new Object[newCapacity];
		size = 0;
		occupied = 0;
		for (Object slot : oldTable) {
				if (slot != null && slot != DELETED) {
                MapEntry<K, V> entry = (MapEntry<K, V>) slot;
                put(entry.getKey(), entry.getValue());
				}
		}
    }
	
	/**
	 * private helper method to check for being prime
	 * @author credit to GeeksforGeeks.org for inspiration on this algorithm
	 * @param n, the int to check for primality
	 * @return, true if n is prime, otherwise false
	 */
	private static boolean isPrime(int n) {
		if (n <= 1) return false;
		if (n == 2) return true;
		if (n % 2 == 0) return false;
		int limit = (int) Math.sqrt(n);
		for (int i = 3; i <= limit; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * private helper method to find the next prime
	 * @param n, the number to start at
	 * @return an int, the next prime equal to or larger than n
	 */
	private static int nextPrime (int n) {
		if (n <= 2)
			return 2;
		if ( n % 2 == 0)
			n++;
		while (!isPrime(n)) {
			n += 2;
		}
		return n;
	}
	
	/**
     * Helper method to cast array from Object to MapEntry<K, V> and suppress warnings
     */
    @SuppressWarnings("unchecked")
    private MapEntry<K, V> entryAt(int index) {
    	return (MapEntry<K, V>) table[index];
    }
}
