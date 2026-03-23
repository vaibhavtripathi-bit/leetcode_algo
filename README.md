# LeetCode Kotlin Solutions

An Android project with high-probability FAANG interview LeetCode solutions in Kotlin.

## Project Structure

```
leetcode/
├── app/                    # Android app module (for running/testing)
├── solutions/              # Kotlin library with all LeetCode solutions
│   └── src/main/java/com/leetcode/solutions/
│       ├── common/         # Shared data structures (ListNode, TreeNode, etc.)
│       └── <category>/     # Problem solutions by category
├── approaches/             # Plain English explanations & pseudo code
└── PLAN.md                 # Project guidelines
```

## Branches

Each problem category has its own branch:
- `main` - Base project setup
- `linked-list` - Linked List problems
- `tree` - Tree problems  
- `graph` - Graph problems
- ... (more to come)

## How to Run

1. Open in Android Studio
2. Sync Gradle
3. Run the app to test solutions
4. Or run unit tests in `solutions` module

## Guidelines

- Simple, readable Kotlin code
- 3-4+ solutions per problem
- Traversal logic separated from business logic
- High probability FAANG questions only (2023-2026)

See [PLAN.md](PLAN.md) for detailed guidelines.
