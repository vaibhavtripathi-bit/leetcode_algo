# Fast-Slow Pointer (Floyd's Algorithm)

## When to Use
- Detecting cycles in linked list
- Finding middle of linked list
- Finding cycle start point
- Happy number problem

## Core Idea
- Slow pointer: moves 1 step
- Fast pointer: moves 2 steps
- If cycle exists, they will meet

## Pseudo Code: Cycle Detection

```
FUNCTION hasCycle(head):
    slow = head
    fast = head
    
    WHILE fast != null AND fast.next != null:
        slow = slow.next
        fast = fast.next.next
        IF slow == fast: RETURN true
    
    RETURN false
```

## Pseudo Code: Find Middle

```
FUNCTION findMiddle(head):
    slow = head
    fast = head
    
    WHILE fast != null AND fast.next != null:
        slow = slow.next
        fast = fast.next.next
    
    RETURN slow  // slow is at middle
```

## Pseudo Code: Find Cycle Start

```
FUNCTION findCycleStart(head):
    // Phase 1: Find meeting point
    slow = fast = head
    WHILE fast AND fast.next:
        slow = slow.next
        fast = fast.next.next
        IF slow == fast: BREAK
    
    IF no cycle: RETURN null
    
    // Phase 2: Find start
    slow = head
    WHILE slow != fast:
        slow = slow.next
        fast = fast.next
    
    RETURN slow
```

## Complexity
- Time: O(n)
- Space: O(1)
