# System Design / Data Structure Design Approach Guide

## Core Idea
Design problems ask you to build a data structure with specific O(1) or O(log n) operations. The interview tests whether you can:
1. Identify the right combination of data structures
2. Maintain invariants across multiple structures

**Common combo:** HashMap (O(1) lookup) + Linked List / Array (O(1) order manipulation)

---

## Pattern 1: Cache with Eviction (LRU / LFU)

### LRU Cache — O(1) get and put
```
Data structures:
  - HashMap<Key, Node>       → O(1) lookup by key
  - Doubly Linked List       → O(1) move-to-front and evict-from-tail

On GET(key):
    node = map[key]
    moveToFront(node)
    RETURN node.value

On PUT(key, value):
    IF key exists:
        map[key].value = value
        moveToFront(map[key])
    ELSE:
        IF size == capacity: evictLRU()  // remove tail
        newNode = createNode(key, value)
        insertAtHead(newNode)
        map[key] = newNode

evictLRU():
    lru = tail.prev   // node just before dummy tail
    removeFromList(lru)
    delete map[lru.key]
```

### LFU Cache — O(1) all operations
```
Data structures:
  - keyToVal:  key → value
  - keyToFreq: key → frequency
  - freqToKeys: freq → LinkedHashSet<key>  (LRU order within same frequency)
  - minFreq:   current minimum frequency

On GET(key):
    incrementFrequency(key)
    RETURN keyToVal[key]

incrementFrequency(key):
    f = keyToFreq[key]
    keyToFreq[key] = f + 1
    freqToKeys[f].remove(key)
    IF freqToKeys[f] is empty AND f == minFreq: minFreq++
    freqToKeys[f+1].add(key)

On evict:
    Remove first element from freqToKeys[minFreq]
```

---

## Pattern 2: O(1) Insert + Delete + GetRandom

```
Data structures:
  - ArrayList         → O(1) random access, O(1) append, O(1) swap-and-pop
  - HashMap<val, idx> → O(1) lookup of element's position

INSERT(val):
    list.append(val)
    map[val] = list.lastIndex

DELETE(val):
    idx = map[val]
    last = list.last()
    list[idx] = last
    map[last] = idx
    list.removeLast()
    map.remove(val)

GET_RANDOM():
    RETURN list[randomInt(0, list.size)]
```

---

## Pattern 3: Min/Max Stack — O(1) getMin/getMax

```
// Each stack element stores (value, currentMin)
PUSH(val):
    currentMin = if stack empty then val else min(val, stack.top.min)
    stack.push((val, currentMin))

GET_MIN():
    RETURN stack.top.min    // O(1)
```

---

## Pattern 4: Two Heaps for Median

```
lowerHalf = MaxHeap   (numbers ≤ median)
upperHalf = MinHeap   (numbers > median)

INVARIANT: lowerHalf.size >= upperHalf.size
           lowerHalf.size - upperHalf.size <= 1

ADD(num):
    lowerHalf.push(num)
    IF upperHalf not empty AND lowerHalf.max > upperHalf.min:
        upperHalf.push(lowerHalf.pop())
    REBALANCE sizes

GET_MEDIAN():
    IF lowerHalf.size > upperHalf.size: RETURN lowerHalf.max
    ELSE: RETURN (lowerHalf.max + upperHalf.min) / 2.0
```

---

## Pattern 5: Iterator Design

```
// Flatten nested lists / implement peek functionality

CONSTRUCTOR: convert/advance to first valid element
HAS_NEXT: return whether next valid element exists
NEXT: return current element, advance pointer
PEEK: return current element without advancing
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| LRU Cache | Medium | HashMap + DLL | All FAANG |
| Min Stack | Medium | Pair Stack | All FAANG |
| Insert Delete GetRandom O(1) | Medium | ArrayList + HashMap | Meta, Google |
| Find Median from Data Stream | Hard | Two Heaps | All FAANG |
| LFU Cache | Hard | 3 HashMaps + minFreq | Google, Amazon, Meta |
| All O`one Data Structure | Hard | DLL Buckets + HashMap | Google |
| Design Twitter | Medium | Heap + HashMap | Meta, Amazon |
| Implement Trie | Medium | Tree nodes | All FAANG |
