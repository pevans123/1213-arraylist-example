package DataStructures;

import ADTs.ListADT; // Assumes ListADT extends CollectionADT
import java.util.NoSuchElementException;
// Other exceptions (IndexOutOfBounds, IllegalArgument) are in java.lang

/**
 * Starter skeleton for ArrayList implementation.
 * Contains only the basic structure, fields, constructor, and method stubs.
 * 
 * @param <T> the type of elements held in this list
 */
public class ArrayList<T> implements ListADT<T> {

  private static final int DEFAULT_CAPACITY = 10;
  private T[] buffer;
  private int size;

  @SuppressWarnings("unchecked")
  public ArrayList() {
    this.buffer = (T[]) new Object[DEFAULT_CAPACITY];
    this.size = 0;
  }

  // --- Stubs for ALL ListADT/CollectionADT methods ---
  // Add Methods
  @Override
  public void add(int index, T item) {
    if (item == null) {
        throw new IllegalArgumentException("Item cannot be null.");
    }
    if (index < 0 || index > this.size) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
    }
    growIfNeeded();
    shiftRight(index);
    this.buffer[index] = item;
    this.size++;
  }

  @Override
  public void addFirst(T item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    growIfNeeded();
    shiftRight(0);
    this.buffer[0] = item;
    this.size++;
  }

  @Override
  public void addLast(T item) {
    if (item == null) {
        throw new IllegalArgumentException("Item cannot be null.");
    }
    growIfNeeded();
    this.buffer[this.size] = item;
    this.size++;
  }

  @Override
  public boolean addAfter(T existing, T item) {
    if (existing == null || item == null) {
        throw new IllegalArgumentException("Existing item and new item cannot be null.");
    }
    int foundIndex = indexOf(existing);
    if (foundIndex >= 0) {
        int insertionIndex = foundIndex + 1;
        growIfNeeded(); 
        shiftRight(insertionIndex); 
        this.buffer[insertionIndex] = item;
        this.size++;
        return true;
      } else {
        return false;
      }
  }

  // Remove Methods
  @Override
  public T removeFirst() {
    if (isEmpty()) {
        throw new NoSuchElementException("List is empty.");
    }
    return remove(0);
  }

  @Override
  public T removeLast() {
    if (isEmpty()) {
        throw new NoSuchElementException("List is empty.");
    }
    int lastIndex = this.size - 1;
    T removedItem = this.buffer[lastIndex];
    this.size--;
    this.buffer[this.size] = null;
    return removedItem;
  }

  @Override
  public T remove(int index) {
    if (index < 0 || index >= this.size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
    }
    T removedItem = this.buffer[index];
    if (index < this.size - 1) {
      shiftLeft(index);
    }
    this.size--;
    this.buffer[this.size] = null;
    return removedItem;
  }

  @Override
  public boolean remove(T item) {
    int foundIndex = indexOf(item);
    if (foundIndex >= 0) {
      remove(foundIndex);
      return true;
    }
    return false;
  }

  // Accessor/Query Methods
  @Override
  public T first() {
    if (isEmpty()) throw new NoSuchElementException("List is empty.");
    return get(0);
  }

  @Override
  public T last() {
    if (isEmpty()) throw new NoSuchElementException("List is empty.");
    return get(this.size - 1);
  }

  @Override
  public T get(int index) {
    if (index < 0 || index >= this.size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
    }
    return this.buffer[index];
  }

  @Override
  public T set(int index, T item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    if (index < 0 || index >= this.size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
    }
    T oldItem = this.buffer[index];
    this.buffer[index] = item;
    return oldItem;
  }

  @Override
  public int indexOf(T item) {
    for (int i = 0; i < this.size; i++) {
      T current = this.buffer[i];
      if (item == null ? current == null : item.equals(current)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public boolean contains(T item) {
    return indexOf(item) >= 0;
  }

  @Override
  public boolean isEmpty() {
    return this.size == 0;
  } // Implemented

  @Override
  public int size() {
    return this.size;
  } // Implemented

  // Clear Method
  @Override
  public void clear() {
    java.util.Arrays.fill(this.buffer, 0, this.size, null);
    this.size = 0;
  }

  // Helper for toString (can be included in starter)
  public String toDetailedString() {
    // Basic stub or the full version from final code
    return "ArrayList[Size=" + size + ", Capacity=" + (buffer != null ? buffer.length : 0)
        + "] (Implementation Pending)";
  }

  @SuppressWarnings("unchecked")

  private void growIfNeeded() {
    if (this.size == this.buffer.length) {

      int oldCapacity = this.buffer.length;
      int newCapacity = (oldCapacity == 0) ? DEFAULT_CAPACITY : oldCapacity * 2;
      Object[] newbuffer = new Object[newCapacity];
      System.arraycopy(this.buffer, 0, newbuffer, 0, this.size);
      this.buffer = (T[]) newbuffer;

    }
  }

  private void shiftRight(int index) {
    int numToMove = this.size - index;
    if (numToMove > 0) {
        System.arraycopy(this.buffer, index, this.buffer, index + 1, numToMove);
    }
  }
  
  private void shiftLeft(int index) {
    
    int numToMove = this.size - 1 - index;
    if (numToMove > 0) {
        System.arraycopy(this.buffer, index + 1, this.buffer, index, numToMove);
    }
  }
}