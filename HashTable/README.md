# Lab: hash tables with separate chaining

The goal for this lab is to implement a hash table that uses separate
chaining. The [`Map`](./Map.java) interface specifies the methods that should be
provided by your `HashTable` class.

- `V get(K key) throws Exception`

    This method returns the value associated with the key,
    if there is one. Otherwise it throws an exception.

- `void put(K key, V value)`

    This method adds a new entry to the table (unless there already
    was an entry for the same key). A later call to `get` with this
    `key` should return this `value`.

- `void remove(K key)`

    This method should remove the entry with the specified key,
    if there is one in the table.

- `boolean containsKey(K key)`

    This method returns true if there is an entry with the specified
    key in the hash table.

You will also need to implement the constructor for `HashTable`,
in which you will need to initialize the table.

As usual, you may create any helper methods that you find useful.

## Testing

Create the file `StudentTest.java` with a class named `StudentTest`
and a method named `test` that thoroughly tests the hash table.

## Submission

Submit your `HashTable.java` and `StudentTest.java` files to the
autograder, projects HashTable and HashTableTests.
