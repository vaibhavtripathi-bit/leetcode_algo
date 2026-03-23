# Graph Approach Guide

## Representations
- **Adjacency List**: `Map<Int, List<Int>>` — sparse graphs (most FAANG problems)
- **Adjacency Matrix**: `Array<IntArray>` — dense graphs
- **Grid**: 2D array where cells are nodes, edges are adjacent cells
- **Edge List**: `List<Triple<Int,Int,Int>>` — (from, to, weight) for weighted graphs

---

## 1. DFS / BFS (Basic Traversal)

### DFS Use Cases
- Connected components, cycle detection, path existence, topological sort

### BFS Use Cases
- **Shortest path in unweighted graph**, level-order, spread simulation

### Pseudocode: Grid DFS (Islands)
```
FUNCTION dfs(r, c):
    IF out of bounds OR visited OR water: RETURN
    MARK visited
    dfs(r+1,c), dfs(r-1,c), dfs(r,c+1), dfs(r,c-1)

FOR each cell:
    IF unvisited land: dfs(cell); count++
```

### Pseudocode: BFS Shortest Path
```
queue = [start], visited = {start}, dist = 0
WHILE queue not empty:
    FOR each node at current level:
        IF node == target: RETURN dist
        ADD unvisited neighbors to queue
    dist++
```

---

## 2. Shortest Path

### Dijkstra (Single Source, Non-Negative Weights)
**Use when:** Find shortest path from one node to all others. Edges have non-negative weights.

```
dist[source] = 0, dist[others] = ∞
minHeap = [(0, source)]

WHILE heap not empty:
    (d, node) = heap.pop()
    
    IF d > dist[node]: CONTINUE   // stale entry
    
    FOR (neighbor, weight) in graph[node]:
        newDist = dist[node] + weight
        IF newDist < dist[neighbor]:
            dist[neighbor] = newDist
            heap.push((newDist, neighbor))

RETURN dist
```
**Time:** O((V + E) log V), **Space:** O(V)

---

### Bellman-Ford (Single Source, Handles Negative Weights)
**Use when:** Graph may have negative edge weights. Detects negative cycles.

```
dist[source] = 0, dist[others] = ∞

REPEAT V-1 times:
    FOR each edge (u, v, weight):
        IF dist[u] + weight < dist[v]:
            dist[v] = dist[u] + weight

// Check for negative cycle (V-th pass)
FOR each edge (u, v, weight):
    IF dist[u] + weight < dist[v]:
        RETURN "negative cycle exists"
```
**Time:** O(V * E), **Space:** O(V)

---

### Floyd-Warshall (All Pairs Shortest Path)
**Use when:** Need shortest path between ALL pairs of nodes.

```
dist[i][j] = direct edge weight (or ∞ if no edge)
dist[i][i] = 0

FOR k = 0 to V-1:     // intermediate node
    FOR i = 0 to V-1:
        FOR j = 0 to V-1:
            dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
```
**Time:** O(V³), **Space:** O(V²)

---

## 3. Topological Sort

**Use when:** Dependencies / ordering with no cycles (DAG).

### Kahn's Algorithm (BFS)
```
inDegree[node] = count of incoming edges
queue = all nodes with inDegree == 0

order = []
WHILE queue not empty:
    node = queue.dequeue()
    order.add(node)
    FOR neighbor: if --inDegree[neighbor] == 0: queue.add(neighbor)

IF order.size == V: RETURN order   // else: cycle exists
```

### DFS-based
```
// Three states: 0=unvisited, 1=in-stack, 2=done
FUNCTION dfs(node):
    IF state[node] == 1: RETURN "cycle"
    IF state[node] == 2: RETURN
    state[node] = 1
    FOR neighbor: dfs(neighbor)
    state[node] = 2
    result.addFirst(node)
```

---

## 4. Union-Find (Disjoint Set Union)

**Use when:** Grouping / merging components, cycle detection in undirected graph, Kruskal's MST.

```
parent = [0..n], rank = [0..n]

FIND(x):                           // with path compression
    IF parent[x] != x: parent[x] = FIND(parent[x])
    RETURN parent[x]

UNION(x, y):                       // union by rank
    px, py = FIND(x), FIND(y)
    IF px == py: RETURN false      // already same component (cycle!)
    IF rank[px] < rank[py]: swap px, py
    parent[py] = px
    IF rank[px] == rank[py]: rank[px]++
    RETURN true
```
**Time:** O(α(n)) ≈ O(1) per operation (inverse Ackermann)

---

## 5. Minimum Spanning Tree

### Kruskal's Algorithm
**Use when:** Connect all nodes with minimum total edge weight.

```
SORT all edges by weight

uf = UnionFind(V)
mst_weight = 0, edges_used = 0

FOR each edge (u, v, w) in sorted order:
    IF uf.union(u, v):          // no cycle
        mst_weight += w
        edges_used++
        IF edges_used == V - 1: BREAK

RETURN mst_weight
```

### Prim's Algorithm (Greedy + Min-Heap)
```
visited = {start}
minHeap = [(0, start)]    // (cost, node)
totalCost = 0

WHILE heap not empty AND visited.size < V:
    (cost, node) = heap.pop()
    IF node already visited: CONTINUE
    visited.add(node)
    totalCost += cost
    FOR (neighbor, weight) in graph[node]:
        IF neighbor not visited:
            heap.push((weight, neighbor))

RETURN totalCost
```

---

## 6. Strongly Connected Components (SCC)

### Kosaraju's Algorithm
**Use when:** Find groups of nodes where every node can reach every other node.

```
// Step 1: DFS on original graph, push to stack in finish order
// Step 2: Transpose the graph (reverse all edges)
// Step 3: DFS on transposed graph in reverse finish order

EACH DFS from step 3 gives one SCC
```

### Tarjan's Algorithm (Single DFS)
```
// Tracks discovery time and low-link value
disc[v] = discovery time
low[v] = lowest disc reachable from subtree of v

IF low[v] == disc[v]: pop stack → one SCC
```

---

## 7. Bipartite Graph

**Definition:** Can color nodes with 2 colors such that no two adjacent nodes share a color.

```
color = [-1, -1, ..., -1]

FUNCTION bfs(start):
    queue = [start], color[start] = 0
    WHILE queue not empty:
        node = queue.dequeue()
        FOR neighbor in graph[node]:
            IF color[neighbor] == -1:
                color[neighbor] = 1 - color[node]
                queue.enqueue(neighbor)
            ELSE IF color[neighbor] == color[node]:
                RETURN false    // same color = not bipartite
    RETURN true
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Algorithm | Asked At |
|---------|-----------|-----------|----------|
| Number of Islands | Medium | DFS/BFS | All FAANG |
| Course Schedule | Medium | Topological Sort | All FAANG |
| Network Delay Time | Medium | Dijkstra | Google, Amazon |
| Cheapest Flights Within K Stops | Medium | Bellman-Ford | Amazon, Google |
| Min Cost to Connect All Points | Medium | Kruskal/Prim (MST) | Google |
| Redundant Connection | Medium | Union-Find | Amazon |
| Number of Connected Components | Medium | Union-Find/DFS | Google |
| Pacific Atlantic Water Flow | Medium | Multi-Source DFS | Google |
| Is Graph Bipartite? | Medium | BFS/DFS Coloring | Amazon |
| Critical Connections (Bridges) | Hard | Tarjan's (SCC) | Amazon |
| Word Ladder | Hard | BFS | All FAANG |
| Alien Dictionary | Hard | Topological Sort | Meta, Google |
