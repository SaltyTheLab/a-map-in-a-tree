package org.example;

// Define the Key-Value Pair class
class KeyValuePair<K extends Comparable<K>, V> implements Comparable<KeyValuePair<K, V>> {
    K key;
    V value;

    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(KeyValuePair<K, V> o) {
        return this.key.compareTo(o.key);
    }
}

// Define the Splay Tree Node
class SplayTreeNode<T extends Comparable<T>> {
    T data;
    SplayTreeNode<T> left, right;

    public SplayTreeNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

// Define the Splay Tree Class
class SplayTree<T extends Comparable<T>> {
    private SplayTreeNode<T> root;

    // Splay operation
    private SplayTreeNode<T> splay(SplayTreeNode<T> root, T key) {
        if (root == null || root.data.compareTo(key) == 0) {
            return root;
        }
        if (key.compareTo(root.data) < 0) { // Key is in left subtree
            if (root.left == null) return root;
            if (key.compareTo(root.left.data) < 0) { // Zig-Zig
                root.left.left = splay(root.left.left, key);
                root = rotateRight(root);
            } else if (key.compareTo(root.left.data) > 0) { // Zig-Zag
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null) root.left = rotateLeft(root.left);
            }
            return (root.left == null) ? root : rotateRight(root);
        } else { // Key is in right subtree
            if (root.right == null) return root;
            if (key.compareTo(root.right.data) > 0) { // Zag-Zag
                root.right.right = splay(root.right.right, key);
                root = rotateLeft(root);
            } else if (key.compareTo(root.right.data) < 0) { // Zag-Zig
                root.right.left = splay(root.right.left, key);
                if (root.right.left != null) root.right = rotateRight(root.right);
            }
            return (root.right == null) ? root : rotateLeft(root);
        }
    }

    // Rotate right
    private SplayTreeNode<T> rotateRight(SplayTreeNode<T> node) {
        SplayTreeNode<T> temp = node.left;
        node.left = temp.right;
        temp.right = node;
        return temp;
    }

    // Rotate left
    private SplayTreeNode<T> rotateLeft(SplayTreeNode<T> node) {
        SplayTreeNode<T> temp = node.right;
        node.right = temp.left;
        temp.left = node;
        return temp;
    }

    // Insert method
    public void insert(T data) {
        if (root == null) {
            root = new SplayTreeNode<>(data);
            return;
        }
        root = splay(root, data);
        if (data.compareTo(root.data) < 0) {
            SplayTreeNode<T> newNode = new SplayTreeNode<>(data);
            newNode.left = root.left;
            newNode.right = root;
            root.left = null;
            root = newNode;
        } else if (data.compareTo(root.data) > 0) {
            SplayTreeNode<T> newNode = new SplayTreeNode<>(data);
            newNode.right = root.right;
            newNode.left = root;
            root.right = null;
            root = newNode;
        }
    }

    // Search method
    public T search(T key) {
        root = splay(root, key);
        return (root != null && root.data.compareTo(key) == 0) ? root.data : null;
    }

    // Delete method
    public void delete(T key) {
        if (root == null) return;
        root = splay(root, key);
        if (root.data.compareTo(key) != 0) return;

        if (root.left == null) {
            root = root.right;
        } else {
            SplayTreeNode<T> temp = root.right;
            root = root.left;
            root = splay(root, key);
            root.right = temp;
        }
    }
}

// Define the TreeMap class
class TreeMap<K extends Comparable<K>, V> {
    private final SplayTree<KeyValuePair<K, V>> splayTree;

    public TreeMap() {
        this.splayTree = new SplayTree<>();
    }

    // Insert key-value pair
    public void insert(K key, V value) {
        KeyValuePair<K, V> pair = new KeyValuePair<>(key, value);
        splayTree.insert(pair);
    }

    // Retrieve value by key
    public V get(K key) {
        KeyValuePair<K, V> pair = splayTree.search(new KeyValuePair<>(key, null));
        return (pair != null) ? pair.value : null;
    }

    // Delete key-value pair
    public void delete(K key) {
        splayTree.delete(new KeyValuePair<>(key, null));
    }
}


