## --- Day 1: Trebuchet?! ---

### Overview
The problem starts with a brief history and them they describe the problem.
The first part is to find the first and the last number in a String and concatenate them.
The second part is also to find the first and the last number in a String and concatenate them, but the numbers are also written in the String.

### My Approach

#### Part 1
In the first part of Day 01's challenge, I focused on processing a list of strings containing digits. My strategy was to:

- **Filter Digits**: Develop a `filterDigits` function in Kotlin that filters out non-digit characters from each string.
- **Sum Digits**: Implement a `sumFilteredDigits` function that sums the numeric values by concatenating the first and last characters of the filtered string.

This part did not involve word-to-number conversion, keeping it straightforward and focused on string manipulation and basic arithmetic.

#### Part 2
For the second part, I added complexity to handle words representing numbers:

- **Word-to-Number Map**: I introduced a `wordsToNumber` map to convert words like "one", "two", etc., into their numeric counterparts.
- **Enhanced Filter Function**: Modified the `filterDigits` function to convert words into numbers based on this map.
- **Adapted Summing Logic**: Applied the updated `filterDigits` in the `sumFilteredDigits` function to handle the new format.

This part showcased the adaptability of the initial setup to accommodate more complex scenarios involving both string and number manipulation.
Also there were some traps in the input, like the **TWONE** word, that I had to handle. To avoid this, e should always parse the input from left to right, and not from right to left.
