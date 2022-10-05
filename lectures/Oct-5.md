# Code Review: Segment Intersection

## Update Height

	protected boolean updateHeight() {
		int oldHeight = this.height;
		int newHeight = max(get_height(this.left), get_height(this.right)) + 1;
		boolean toReturn = oldHeight != newHeight;
		this.height = newHeight;
		return toReturn;
	}
	
## Another Update Height

	protected boolean updateHeight() {
		int r_height;
		int l_height;
		if(right == null) r_height = -1;
		else r_height = right.height;
		if(left == null) l_height = -1;
		else l_height = left.height;

		int prev = this.height;
		if(l_height > r_height) {
			this.height = l_height +1;
		} else {
			this.height = r_height +1;
		}
		return (prev != this.height);
	}

## Search

    public Node search(K key) {
        if (this.root != null) {
            if (this.find(key, this.root, this.root.parent).data.equals(key)) {
                return this.find(key, this.root, this.root.parent);
            }
        }
        return null;
    }

## Another Search

    public Node search(K key) {
        Node suspect = find(key, root, null);
        if(!suspect.get().equals(key)) {
            return null;
        }
        return suspect;
    }

## Right Rotate

    public Node rightRotate(Node y) {
        Node x = y.left;
        Node T1 = y.right;
        Node T3 = x.left;
        Node T2 = x.right;
        K yData = y.data;
        K xData = x.data;

        y.data = xData;
        y.right = new Node(yData);
        y.right.parent = y;
        y.right.left = T2;
        if (T2 != null) {
            T2.parent = y.right;
            T2.updateHeight();
        }
        y.right.right = T1;
        if (T1 != null) {
            T1.parent = y.right;
            T1.updateHeight();
        }
        y.left = T3;
        if (T3 != null) {
            T3.parent = y;
            T3.updateHeight();
        }

        Node temp = y.right;
        while (temp != null) {
            temp.updateHeight();
            temp = temp.parent;
        }
        return y;
    }


## Another Right Rotate

    void set_left(Node<K> n, Node<K> l) {
        n.left = l;
        if (l != null) l.parent = n;
        n.updateHeight();
    }
	
    void set_right(Node<K> n, Node<K> r) {
        n.right = r;
        if (r != null) r.parent = n;
        n.updateHeight();
    }

    // Replace node n1 with node n2.
    void replace(Node<K> n1, Node<K> n2) {
        if (n1.parent == null) {
            root = n2;
            if (n2 != null) n2.parent = null;
        } else {
            if (n1 == n1.parent.left) {
                set_left(n1.parent, n2);
            } else if (n1 == n1.parent.right) {
                set_right(n1.parent, n2);
            }
        }
    }

    Node right_rotate(Node y) {
        Node x = y.left;
		assert x != null;
        Node B = x.right;
        replace(y, x);
        set_left(y, B);
        set_right(x, y);
        return x;
    }
	

## AVL Insert

    public Node insert(K key) {
        if (this.root == null) {
            this.root = new Node(key);
            this.numNodes = 1;
            return this.root;
        }
        if (this.contains(key)) {
            return null;
        }
        Node addParent = find(key, this.root, this.root.parent);
        Node toReturn;
        if (lessThan.test(key, addParent.data)) {
            addParent.left = new Node(key);
            addParent.updateHeight();
            addParent.left.parent = addParent;
            this.numNodes += 1;
            toReturn = addParent.left;
        } else {
            addParent.right = new Node(key);
            addParent.updateHeight();
            addParent.right.parent = addParent;
            this.numNodes += 1;
            toReturn = addParent.right;
        }
        Node temp = toReturn;
        while (temp != null) {
            temp.updateHeight();
            temp = temp.parent;
        }
        Node toCheck = toReturn;

        while (toCheck != null) {
            if (!(toCheck.isAVL())) {
                int loadFactor = (get_height(toCheck.left) - get_height(toCheck.right));
                // Left Left case
                if (loadFactor > 1 && lessThan.test(key, toCheck.left.data)) {
                    temp =  rightRotate(toCheck);
                    return temp;
                }
                // Right Right Case
                else if (loadFactor < -1 && !(lessThan.test(key, toCheck.right.data))) {
                    temp = leftRotate(toCheck);
                    return temp;
                }
                // Left Right Case
                else if (loadFactor > 1 && !(lessThan.test(key, toCheck.left.data))) {
                    toCheck.left = leftRotate(toCheck.left);
                    temp = rightRotate(toCheck);
                    return temp;
                }
                // Right Left Case
                else if (loadFactor < -1 && lessThan.test(key, toCheck.right.data)) {
                    toCheck.right = rightRotate(toCheck.right);
                    temp = leftRotate(toCheck);
                    return temp;
                }
            }
            toCheck = toCheck.parent;
        }
        return null;
    }

## Another AVL Insert

    public Node insert(K key) {
        Node n = super.insert(key);
        Node parent = n.parent;
        if (n == null) {
            return n;
        } else {
            if (parent == null){
                root = makeAVL(root,root);
                return root;
            }
            while (parent != null){
                if (!parent.isAVL()){
                    root = makeAVL(root,parent);
                    return root;
                }else{
                    parent = parent.parent;
                }
            }
            return root;
        }
    }

    public Node makeAVL(Node root, Node n){
        if (n == null)
            return root;
        else if (n.isAVL()){
            return makeAVL(root,n.parent);
        }
        else if (get_height(n.left) <= get_height(n.right)){
            if (get_height(n.right.left) <= get_height(n.right.right)){
                Node y = rotateLeft(n);
                root = getRoot(y);
                return makeAVL(root,y.parent);
            }else{
                n.right = rotateRight(n.right);
                n.right.parent = n;
                Node y = rotateLeft(n);
                root = getRoot(y);
                return makeAVL(root,y.parent);
            }
        }else{
            if (get_height(n.left.left) < get_height(n.left.right)){
                n.left = rotateLeft(n.left);
                n.left.parent = n;
                Node y = rotateRight(n);
                root = getRoot(y);
                return makeAVL(root,y.parent);
            }else{
                Node y = rotateRight(n);
                root = getRoot(y);
                return makeAVL(root,y.parent);
            }
        }
    }


## Yet Another AVL Insert

    public Node insert(K key) {
        Node q = super.insert(key);
        fixups(q);
        return q;
    }
	
    private void fixups(Node p) {
        while (p != null) {
            p = rebalance(p);
            p.updateHeight();
            p = p.parent;
        }
    }
	
    private Node rebalance(Node node){
        if (! isAVL(node)) {
            if (get_height(node.left) <= get_height(node.right)) {
                if (get_height(node.right.left) <= get_height(node.right.right)) {
                    return left_rotate(node);
                } else {
                    Node r = right_rotate(node.right);
                    set_right(node, r);
                    return left_rotate(node);
                }
            } else { // get_height(node.left) > get_height(node.right)
                if (get_height(node.left.left) < get_height(node.left.right)) {
                    Node l = left_rotate(node.left);
                    set_left(node, l);
                    return right_rotate(node);
                } else { // get_height(node.left.left) >= get_height(node.left.right)
                    return right_rotate(node);
                }
            }
        } else {
            return node;
        }
    }
	
## AVL Remove

    public void remove(K key) {
        if (!(this.contains(key))) {
            return;
        }
        Node deleteMe = this.search(key);

        if (deleteMe.left != null && deleteMe.right != null) {
            deleteWithTwoKids(key);
        } else if (deleteMe.isLeaf()){
            deleteLeaf(key);
        } else {
            deleteWithOneChild(key);
        }
        numNodes -= 1;
    }

    private void deleteLeaf(K key) {
        Node deleteMe = this.search(key);
        if (deleteMe.equals(root))
            this.clear();
            return;
        }
        Node parent = deleteMe.parent;
        if (deleteMe.equals(parent.left)) {
            parent.left = null;
        } else if (deleteMe.equals(parent.right)) {
            parent.right = null;
        }
        updateHeightHelper(deleteMe);
        if (parent != null) {
            balance(parent);
        }
    }

    private void deleteWithTwoKids(K key) {
        Node deleteMe = this.search(key);
        Node replacer = deleteMe.right.first();
        Node replacerParent = replacer.parent;
        deleteMe.data = replacer.data;

        if (deleteMe.right.equals(replacer)) {
            deleteMe.right = replacer.right;
            if (replacer.right != null) {
                replacer.right.parent = deleteMe;
            }
        } else {
            replacerParent.left = replacer.right;
            if (replacer.right != null) {
                replacer.right.parent = replacer.parent;
            }
        }
        updateHeightHelper(replacer);
        if (replacer != null) {
            balance(replacer);
        }
    }
