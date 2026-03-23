# Graph Approach Guide

## Representations
- **Adjacency List**: `Map<Int, List<Int>>` — sparse graphs (most FAANG problems)
- **Adjacency Matrix**: `Array<IntArray>` — dense graphs
- **Grid**: 2D array where cells are nodes, edges are adjacent cells

---

## Pattern 1: DFS (Depth-First Search)

### When to Use
- Connected components
- Detect cycle
- Find path between nodes
- Island counting
- Topological sort

### Pseudocode: DFS on Graph
```
visited = empty set

FUNCTION dfs(node):
    IF node in visited: RETURN
    visited.add(node)
    
    PROCESS(node)    // business logic here
    
    FOR neighbor in graph[node]:
        dfs(neighbor)
```

### Pseudocode: DFS Iterative
```
stack = [start]
visited = empty set

WHILE stack not empty:
    node = stack.pop()
    
    IF node in visited: CONTINUE
    visited.add(node)
    
    PROCESS(node)
    
    FOR neighbor in graph[node]:
        IF neighbor not in visited:
            stack.push(neighbor)
```

### Pseudocode: Islands (Grid DFS)
```
count = 0

FOR each cell (r, c) in grid:
    IF grid[r][c] == '1' AND not visited:
        dfs(r, c)    // marks entire island as visited
        count++

FUNCTION dfs(r, c):
    IF out of bounds OR visited OR water: RETURN
    
    MARK visited
    dfs(r+1, c), dfs(r-1, c)    // down, up
    dfs(r, c+1), dfs(r, c-1)    // right, left
```

---

## Pattern 2: BFS (Breadth-First Search)

### When to Use
- **Shortest path** in unweighted graph
- Level-by-level processing
- Find minimum steps
- Rotten oranges / spreading

### Pseudocode: BFS Shortest Path
```
queue = [start]
visited = {start}
distance = 0

WHILE queue not empty:
    size = queue.size    // current level
    
    FOR i = 0 to size - 1:
        node = queue.dequeue()
        
        IF node == target: RETURN distance
        
        FOR neighbor in graph[node]:
            IF neighbor not visited:
                visited.add(neighbor)
                queue.enqueue(neighbor)
    
    distance++

RETURN -1   // not reachable
```

---

## Pattern 3: Topological Sort (DAG)

### When to Use
- Course scheduling (prerequisites)
- Build order / dependency resolution
- Detect cycle in directed graph

### Method A: Kahn's Algorithm (BFS-based)
```
// Step 1: Calculate in-degree for all nodes
inDegree[node] = count of incoming edges

// Step 2: Start with nodes of in-degree 0
queue = all nodes with inDegree == 0

order = []
WHILE queue not empty:
    node = queue.dequeue()
    order.add(node)
    
    FOR neighbor in graph[node]:
        inDegree[neighbor]--
        IF inDegree[neighbor] == 0:
            queue.enqueue(neighbor)

IF order.size == total nodes: RETURN order    // valid topological order
ELSE: RETURN "cycle exists"
```

### Method B: DFS-based
```
visited = empty set
onStack = empty set    // detect back edge (cycle)
result = []

FUNCTION dfs(node):
    IF node in onStack: RETURN "cycle"
    IF node in visited: RETURN
    
    onStack.add(node)
    
    FOR neighbor in graph[node]:
        dfs(neighbor)
    
    onStack.remove(node)
    visited.add(node)
    result.addFirst(node)    // add in reverse post-order

FOR each node: dfs(node)
RETURN result
```

---

## Pattern 4: Union-Find (Disjoint Set)

### When to Use
- Connected components
- Cycle detection in undirected graph
- Minimum spanning tree (Kruskal)

### Pseudocode
```
parent = [0, 1, 2, ..., n]    // each node is its own root
rank = [0, 0, ..., 0]

FUNCTION find(x):
    IF parent[x] != x:
        parent[x] = find(parent[x])   // path compression
    RETURN parent[x]

FUNCTION union(x, y):
    rootX = find(x)
    rootY = find(y)
    
    IF rootX == rootY: RETURN false    // already connected (cycle!)
    
    // Union by rank
    IF rank[rootX] < rank[rootY]: swap rootX, rootY
    parent[rootY] = rootX
    IF rank[rootX] == rank[rootY]: rank[rootX]++
    
    RETURN true
```

---

## Pattern 5: Multi-Source BFS

### When to Use
- Rotten oranges (spread from multiple sources simultaneously)
- 01 matrix (distance from nearest 0)
- Pacific Atlantic water flow

### Core Idea: Start BFS from ALL sources simultaneously
```
queue = ALL starting nodes    // add multiple sources
visited = mark all sources as visited

WHILE queue not empty:
    node = queue.dequeue()
    FOR neighbor in neighbors(node):
        IF can reach AND not visited:
            visited.add(neighbor)
            queue.enqueue(neighbor)
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Number of Islands | Medium | Grid DFS/BFS | All FAANG |
| Clone Graph | Medium | DFS/BFS | Meta, Amazon |
| Course Schedule | Medium | Topological Sort | All FAANG |
| Course Schedule II | Medium | Topological Sort | Google, Amazon |
| Pacific Atlantic Water Flow | Medium | Multi-Source DFS | Google |
| Max Area of Island | Medium | Grid DFS | Amazon, Google |
| Rotting Oranges | Medium | Multi-Source BFS | Amazon, Google |
| Number of Connected Components | Medium | Union-Find/DFS | Google, LinkedIn |
| Word Ladder | Hard | BFS | All FAANG |
| Graph Valid Tree | Medium | Union-Find | LinkedIn |
