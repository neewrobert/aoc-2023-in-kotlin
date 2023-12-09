## --- Day 3: Gear Ratios ---
### Overview
Day 3's challenge revolves around an engine schematic, where participants have to analyze and solve a problem to help an Elf and an engineer. 
In Part One, the task is to identify valid numbers in the schematic based on their proximity to certain symbols, and then sum these numbers. 
Part Two extends the challenge by requiring players to identify "gears" in the engine - symbols adjacent to exactly two part numbers. 
Participants must calculate the gear ratio by multiplying these numbers and summing all the ratios to identify which gear needs replacement. 
This challenge emphasizes pattern recognition and arithmetic within a grid of characters.

### My Approach

#### Part 1
In Part 1, I developed an approach to analyze an engine schematic represented in a grid format. 
Using Kotlin, I processed each line of the input to identify numbers adjacent to specific symbols. 
A custom function, `processLine`, was employed to build numbers from consecutive digits and sum them only if they were near a symbol. 
This part required careful string parsing and conditional logic to determine which numbers in the grid contributed to the final sum.

#### Part 2
For Part 2, the challenge was to identify "gears" in the schematic, which are asterisks adjacent to exactly two part numbers.
I enhanced the grid processing to identify these gears and store the adjacent numbers using `processLineForParts`. 
The critical task was to calculate the gear ratio by multiplying these numbers. 
A map, `partsByGear`, was used to keep track of the parts associated with each gear. 
The sum of these products constituted the final answer, emphasizing pattern recognition and arithmetic operations on the grid.