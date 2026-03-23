# Linked List Approach Guide

## Core Patterns

---

## 1. Fast-Slow Pointer (Floyd's Algorithm)

### When to Use
- Find middle of linked list
- Detect cycle
- Find cycle start
- Find Nth from end (with variation)

### Core Idea
Two pointers move at different speeds. Slow moves 1 step, fast moves 2 steps. When fast reaches the end, slow is at the middle. If there's a cycle, they will eventually collide.

### Pseudocode: Find Middle
```
slow = head
fast = head

WHILE fast != null AND fast.next != null:
    slow = slow.next
    fast = fast.next.next

RETURN slow  // slow is now at middle
```

### Pseudocode: Detect Cycle
```
slow = head
fast = head

WHILE fast != null AND fast.next != null:
    slow = slow.next
    fast = fast.next.next
    IF slow == fast:
        RETURN true  // cycle exists

RETURN false
```

### Pseudocode: Find Cycle Start (2-Phase)
```
// Phase 1: Find meeting point
slow = head, fast = head
WHILE fast != null AND fast.next != null:
    slow = slow.next
    fast = fast.next.next
    IF slow == fast: BREAK

IF no collision: RETURN null

// Phase 2: Move one pointer to head, walk together
slow = head
WHILE slow != fast:
    slow = slow.next
    fast = fast.next

RETURN slow  // this is the cycle start
```

**Why it works:** If distance from head to cycle start = `a`, and cycle length = `c`, then `a = n*c - b` (where b is distance from cycle start to meeting point). So walking `a` steps from head equals walking `a` steps from meeting point — both land on cycle start.

---

## 2. Reversal Pattern

### When to Use
- Reverse entire list
- Reverse portion of list
- Palindrome check
- Reorder list

### Core Idea
Use three pointers: `prev`, `current`, `next`. Reverse each link one by one.

### Pseudocode: Reverse Entire List
```
prev = null
current = head

WHILE current != null:
    next = current.next    // save next
    current.next = prev    // reverse link
    prev = current         // move prev forward
    current = next         // move current forward

RETURN prev  // new head
```

### Pseudocode: Reverse Between Positions (L to R)
```
dummy.next = head
prev = dummy

// Walk to node just before L
FOR i = 1 to L-1:
    prev = prev.next

start = prev.next   // first node of section to reverse
then = start.next   // node to be reversed next

FOR i = 1 to R-L:
    start.next = then.next
    then.next = prev.next
    prev.next = then
    then = start.next

RETURN dummy.next
```

---

## 3. Dummy Node Pattern

### When to Use
- Merging lists
- Removing nodes (avoids head edge case)
- Building new list from existing

### Core Idea
Create a fake dummy node before head. This eliminates the edge case of updating the head pointer.

### Pseudocode: Merge Two Sorted Lists
```
dummy = new Node(0)
tail = dummy

WHILE l1 != null AND l2 != null:
    IF l1.val <= l2.val:
        tail.next = l1
        l1 = l1.next
    ELSE:
        tail.next = l2
        l2 = l2.next
    tail = tail.next

tail.next = l1 OR l2  // attach remaining

RETURN dummy.next
```

---

## 4. Runner / Fixed Gap Pointer

### When to Use
- Remove Nth node from end
- Check if palindrome

### Core Idea
Advance one pointer N steps ahead, then move both together. When the fast one hits the end, the slow one is exactly N from end.

### Pseudocode: Remove Nth From End
```
dummy.next = head
fast = dummy
slow = dummy

// advance fast N+1 steps
FOR i = 0 to N:
    fast = fast.next

// move both until fast hits end
WHILE fast != null:
    fast = fast.next
    slow = slow.next

// slow is now just before the target
slow.next = slow.next.next

RETURN dummy.next
```

---

## 5. Two Pointer Convergence

### When to Use
- Find intersection of two lists
- Compare from both ends

### Pseudocode: Intersection of Two Lists (Elegant Switch)
```
p1 = headA
p2 = headB

WHILE p1 != p2:
    p1 = IF p1 == null THEN headB ELSE p1.next
    p2 = IF p2 == null THEN headA ELSE p2.next

RETURN p1
```

**Why it works:** Both pointers travel distance `a + b + c` (where `c` is shared length). They meet exactly at intersection.

---

## 6. In-Place Merge / Interleave

### When to Use
- Reorder list (L0→Ln→L1→Ln-1)
- Merge two parts of same list

### Pseudocode: Reorder List
```
// Step 1: Find middle
middle = findMiddle(head)

// Step 2: Reverse second half
secondHalf = reverse(middle.next)
middle.next = null

// Step 3: Interleave
l1 = head, l2 = secondHalf
WHILE l2 != null:
    tmp1 = l1.next
    tmp2 = l2.next
    l1.next = l2
    l2.next = tmp1
    l1 = tmp1
    l2 = tmp2
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern Used | Asked At |
|---------|-----------|--------------|----------|
| Reverse Linked List | Easy | Reversal | Google, Meta, Amazon |
| Merge Two Sorted Lists | Easy | Dummy Node | All FAANG |
| Linked List Cycle | Easy | Fast-Slow | All FAANG |
| Middle of Linked List | Easy | Fast-Slow | Google, Amazon |
| Palindrome Linked List | Easy | Fast-Slow + Reversal | Google, Meta |
| Intersection of Two Lists | Easy | Two Pointer Switch | All FAANG |
| Remove Nth From End | Medium | Runner / Fixed Gap | All FAANG |
| Add Two Numbers | Medium | Dummy Node + Carry | All FAANG |
| Reorder List | Medium | Middle + Reverse + Merge | Google, Meta |
| Sort List | Medium | Merge Sort | Amazon, Google |
| Linked List Cycle II | Medium | Floyd's 2-Phase | Google, Meta |
| Reverse Linked List II | Medium | Reversal | Amazon, Microsoft |
| Merge K Sorted Lists | Hard | Heap / Divide & Conquer | All FAANG |
| Reverse Nodes in K-Group | Hard | Reversal in chunks | Google, Meta |
