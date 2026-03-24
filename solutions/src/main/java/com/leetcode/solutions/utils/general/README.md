# utils/general — Common Conversions & General Utilities

These are the helpers that don't belong to any specific algorithm category
but that you reach for constantly while writing any code.

---

## BaseConversions.kt

| Function | Example | Notes |
|---|---|---|
| `Int.toBaseString(base)` | `255.toBaseString(16)` → `"ff"` | Base 2–36 |
| `String.fromBase(base)` | `"ff".fromBase(16)` → `255` | Parse any base |
| `Int.toHexString()` | `255.toHexString()` → `"ff"` | Lowercase, no prefix |
| `Int.toOctalString()` | `8.toOctalString()` → `"10"` | |
| `String.hexToInt()` | `"ff".hexToInt()` → `255` | |
| `String.octalToInt()` | `"10".octalToInt()` → `8` | |
| `Int.toRoman()` | `1994.toRoman()` → `"MCMXCIV"` | LC 12 |
| `String.romanToInt()` | `"MCMXCIV".romanToInt()` → `1994` | LC 13 |
| `Int.toEnglishWords()` | `1234567.toEnglishWords()` | LC 273 |

---

## TypeConversions.kt

| Function | Example | Notes |
|---|---|---|
| `IntArray.toIntList()` | `intArrayOf(1,2,3).toIntList()` | → `List<Int>` |
| `List<Int>.toIntArray()` | `listOf(1,2,3).toIntArray()` | → `IntArray` |
| `Array<Int>.toPrimitiveIntArray()` | Unbox `Array<Int>` → `IntArray` | |
| `Array<IntArray>.toListOfLists()` | Matrix conversion | |
| `List<List<Int>>.toArrayOfIntArrays()` | Matrix conversion | |
| `String.toAsciiArray()` | `"abc"` → `[97,98,99]` | |
| `IntArray.asciiToString()` | `[97,98,99]` → `"abc"` | |
| `String.toDigitList()` | `"123"` → `[1,2,3]` | |
| `Char.toAscii()` | `'A'.toAscii()` → `65` | |
| `Int.toAsciiChar()` | `65.toAsciiChar()` → `'A'` | |
| `Boolean.toInt()` | `true.toInt()` → `1` | |
| `Int.toBool()` | `0.toBool()` → `false` | |
| `Long.toIntSafe()` | Clamped, no exception | |
| `IntArray.toPair()` | `intArrayOf(3,5).toPair()` → `(3,5)` | |
| `IntArray.toTriple()` | `intArrayOf(1,2,3).toTriple()` | |

---

## StringManipUtils.kt

| Function | Example | Notes |
|---|---|---|
| `String.reverseEachWord()` | `"hello world"` → `"olleh dlrow"` | |
| `String.reverseWords()` | `"  the sky  "` → `"sky the"` | LC 151 |
| `String.capitalize()` | `"hELLO"` → `"Hello"` | |
| `String.titleCase()` | `"hello world"` → `"Hello World"` | |
| `String.camelToSnake()` | `"helloWorld"` → `"hello_world"` | |
| `String.snakeToCamel()` | `"hello_world"` → `"helloWorld"` | |
| `String.countChar(c)` | `"hello".countChar('l')` → `2` | |
| `String.countSubstring(sub)` | `"abab".countSubstring("ab")` → `2` | |
| `String.firstUniqueCharIndex()` | `"leetcode"` → `0` | LC 387 |
| `String.mostFrequentChar()` | `"aabbc"` → `'a'` | |
| `String.words()` | `"  hi  there  "` → `["hi","there"]` | |
| `String.removeAllWhitespace()` | `"h e l l o"` → `"hello"` | |
| `String.keepAlphanumeric()` | `"a!b@c"` → `"abc"` | |
| `String.runLengthEncode()` | `"aaabbc"` → `"a3b2c1"` | LC 443 |
| `String.runLengthDecode()` | `"a3b2c1"` → `"aaabbc"` | |
| `String.isNumeric()` | `"123"` → `true` | |

---

## CollectionConversions.kt

| Function | Example | Notes |
|---|---|---|
| `Map<K,V>.toPairList()` | Map → List of Pairs | |
| `List<Pair<K,V>>.toMap()` | Pairs → Map | |
| `List<List<T>>.flatten()` | `[[1,2],[3]]` → `[1,2,3]` | |
| `Array<IntArray>.flatten()` | Matrix → flat IntArray | |
| `List<T>.groupIntoChunks(n)` | `[1,2,3,4].chunked(2)` | |
| `List<A>.zipWith(other)` | Zip two lists into pairs | |
| `List<Pair<A,B>>.unzip()` | Pair list → two lists | |
| `IntArray.zipWith(other)` | Zip two int arrays | |
| `List<T>.toDeque()` | List → ArrayDeque | |
| `ArrayDeque<T>.drainToList()` | Empties deque into list | |
| `List<T>.intersectWith(other)` | Common elements | |
| `List<T>.unionWith(other)` | All unique elements | |
| `List<T>.differenceWith(other)` | Elements only in first | |
| `List<T>.rotateLeft(k)` | `[1,2,3,4,5].rotateLeft(2)` → `[3,4,5,1,2]` | LC 189 concept |
| `List<T>.rotateRight(k)` | Rotate right | |
| `List<List<T>>.transpose()` | Rows ↔ columns | |
| `Array<IntArray>.transpose()` | Matrix transpose | |

---

## RangeUtils.kt

| Function | Example | Notes |
|---|---|---|
| `clamp(value, lo, hi)` | `clamp(15, 0, 10)` → `10` | Int/Long/Double |
| `Int.clampTo(lo, hi)` | `15.clampTo(0, 10)` → `10` | Extension form |
| `Int.isInRange(lo, hi)` | `5.isInRange(1,10)` → `true` | Inclusive |
| `Int.isBetween(lo, hi)` | `5.isBetween(1,10)` → `true` | Exclusive |
| `Int.isValidIndex(size)` | `3.isValidIndex(5)` → `true` | Bounds check |
| `rangeList(start, end)` | `rangeList(1,5)` → `[1,2,3,4,5]` | |
| `rangeList(start, end, step)` | `rangeList(0,10,2)` → `[0,2,4,6,8,10]` | |
| `indexRange(n)` | `indexRange(3)` → `[0,1,2]` | |
| `overlapLength(a0,a1,b0,b1)` | `overlapLength(1,5,3,8)` → `2` | |
| `mergeRange(a0,a1,b0,b1)` | `mergeRange(1,5,3,8)` → `(1,8)` | |
| `maxOfAll(vararg)` | `maxOfAll(3,1,4,9,2)` → `9` | |
| `minOfAll(vararg)` | `minOfAll(3,1,4,9,2)` → `1` | |
| `circularIndex(idx, size)` | `circularIndex(-1,5)` → `4` | Circular arrays |
| `ceilDiv(a, b)` | `ceilDiv(7,2)` → `4` | No floats |
| `floorDiv(a, b)` | `floorDiv(7,2)` → `3` | Handles negatives |
