# Tree Approach Guide

## Core Traversal Patterns

---

## 1. DFS — Recursive (Most Natural for Trees)

### Core Idea
Visit nodes by going deep first. The call stack handles the "memory". At every node, decide what to do before, between, or after visiting children.

### Inorder (Left → Root → Right) — gives sorted order for BST
```
FUNCTION inorder(node):
    IF node == null: RETURN
    inorder(node.left)
    PROCESS(node)          // business logic here
    inorder(node.right)
```

### Preorder (Root → Left → Right) — good for copying/serializing tree
```
FUNCTION preorder(node):
    IF node == null: RETURN
    PROCESS(node)
    preorder(node.left)
    preorder(node.right)
```

### Postorder (Left → Right → Root) — good for deletion, height calc
```
FUNCTION postorder(node):
    IF node == null: RETURN
    postorder(node.left)
    postorder(node.right)
    PROCESS(node)          // process after children
```

---

## 2. DFS — Iterative with Stack

### When to Use
- When recursion stack overflow is a concern (very deep trees)
- When you need more control over traversal

### Pseudocode: Inorder Iterative
```
stack = empty
current = root

WHILE current != null OR stack not empty:
    // Go as far left as possible
    WHILE current != null:
        stack.push(current)
        current = current.left
    
    // Process node
    current = stack.pop()
    PROCESS(current)
    
    // Move to right subtree
    current = current.right
```

### Pseudocode: Preorder Iterative
```
IF root == null: RETURN
stack = [root]

WHILE stack not empty:
    node = stack.pop()
    PROCESS(node)
    
    // Push right first so left is processed first
    IF node.right != null: stack.push(node.right)
    IF node.left != null: stack.push(node.left)
```

---

## 3. BFS — Level Order with Queue

### When to Use
- Level-by-level processing
- Find shortest path in unweighted tree
- Right/left side view
- Zigzag traversal

### Core Idea
Use a queue. Process all nodes at current level before going to next.

### Pseudocode: Level Order
```
IF root == null: RETURN

queue = [root]

WHILE queue not empty:
    levelSize = queue.size
    
    FOR i = 0 to levelSize - 1:
        node = queue.dequeue()
        PROCESS(node)
        
        IF node.left != null: queue.enqueue(node.left)
        IF node.right != null: queue.enqueue(node.right)
```

### Pseudocode: Right Side View
```
FOR each level:
    last node in level is the right side view
```

---

## 4. Return Value Pattern (Most Important for FAANG)

### Core Idea
Instead of using global variables, return ALL needed information from recursive calls. This keeps functions pure and easier to reason about.

### Pseudocode: Height + Diameter in one pass
```
FUNCTION dfs(node) -> (diameter, height):
    IF node == null: RETURN (0, 0)
    
    (leftDiam, leftHeight) = dfs(node.left)
    (rightDiam, rightHeight) = dfs(node.right)
    
    currentDiameter = leftHeight + rightHeight
    maxDiam = max(currentDiameter, leftDiam, rightDiam)
    height = 1 + max(leftHeight, rightHeight)
    
    RETURN (maxDiam, height)
```

---

## 5. BST Properties Pattern

### Core Idea
BST: left < root < right. Inorder traversal gives sorted order. Use range validation.

### Pseudocode: Validate BST
```
FUNCTION validate(node, min, max):
    IF node == null: RETURN true
    
    IF node.val <= min: RETURN false
    IF node.val >= max: RETURN false
    
    RETURN validate(node.left, min, node.val)
        AND validate(node.right, node.val, max)

// Initial call:
validate(root, -infinity, +infinity)
```

---

## 6. Path Sum Pattern

### When to Use
- Has path with target sum?
- Find all paths
- Maximum path sum

### Pseudocode: Path Sum (root to leaf)
```
FUNCTION hasPath(node, remaining):
    IF node == null: RETURN false
    
    remaining = remaining - node.val
    
    IF isLeaf(node): RETURN remaining == 0
    
    RETURN hasPath(node.left, remaining)
        OR hasPath(node.right, remaining)
```

### Pseudocode: Max Path Sum (any path)
```
globalMax = -infinity

FUNCTION maxGain(node):
    IF node == null: RETURN 0
    
    leftGain = max(0, maxGain(node.left))   // ignore if negative
    rightGain = max(0, maxGain(node.right))
    
    // Update global max (path through this node)
    globalMax = max(globalMax, node.val + leftGain + rightGain)
    
    // Return max gain going in ONE direction only
    RETURN node.val + max(leftGain, rightGain)
```

---

## 7. LCA (Lowest Common Ancestor) Pattern

### Core Idea
Recursively search both subtrees. If p or q found, return it. When both left and right return non-null, current node is LCA.

### Pseudocode: LCA Binary Tree
```
FUNCTION lca(node, p, q):
    IF node == null OR node == p OR node == q:
        RETURN node
    
    left = lca(node.left, p, q)
    right = lca(node.right, p, q)
    
    IF left != null AND right != null:
        RETURN node   // p and q are in different subtrees
    
    RETURN left OR right   // one subtree has both
```

---

## 8. Serialize / Deserialize Pattern

### Pseudocode: Serialize (Preorder)
```
FUNCTION serialize(node):
    IF node == null: RETURN "null,"
    RETURN node.val + "," + serialize(node.left) + serialize(node.right)
```

### Pseudocode: Deserialize
```
FUNCTION deserialize(tokens, index):
    val = tokens[index++]
    IF val == "null": RETURN null
    
    node = new TreeNode(val)
    node.left = deserialize(tokens, index)
    node.right = deserialize(tokens, index)
    RETURN node
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Max Depth | Easy | Recursive DFS | All FAANG |
| Invert Binary Tree | Easy | Recursive DFS | Google, Meta |
| Symmetric Tree | Easy | Mirror DFS | All FAANG |
| Diameter | Easy | Return Value Pattern | Google, Amazon |
| Subtree Check | Easy | Recursive + isSame | Amazon, Meta |
| Level Order Traversal | Medium | BFS | All FAANG |
| Validate BST | Medium | BST Range | All FAANG |
| LCA | Medium | LCA Pattern | All FAANG |
| Construct from Pre+In | Medium | Divide & Conquer | Google, Meta |
| Right Side View | Medium | BFS | Amazon, Meta |
| Kth Smallest in BST | Medium | Inorder | Google, Amazon |
| Path Sum II | Medium | DFS Path Tracking | Amazon |
| Max Path Sum | Hard | Return Value Pattern | All FAANG |
| Serialize/Deserialize | Hard | Preorder DFS | All FAANG |
| Binary Tree Cameras | Hard | Postorder DP | Google |
