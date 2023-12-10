## --- Day 10: Pipe Maze ---

### Overview
"Day 10: Pipe Maze" is a coding challenge where the goal is to navigate through a grid-based maze. 
The challenge involves finding a loop (a path that starts and ends at the same point) in a maze represented as a grid of characters.
Each character in the grid represents a different type of path or connection.

### My Approach

#### Part 1
For this first part of the challenge, I decided to use a recursive approach to find the loop.
I adapted a Python code from an EDX course on Artificial Intelligence and translated it into Kotlin. 
This challenge was my first experience using the tail recursive functions in Kotlin. 
The core of my solution involves navigating through a grid, identifying paths and loops based on specific symbols and connection states, and determining the start and end points of loops.
The use of tail recursion optimizes the process, making the code more efficient and Kotlin idiomatic.


- **Grid Parsing:** Converting the input list of strings into a structured grid (`Array<CharArray>`).
- **Directional Logic:** Using directional pairs (`Pair<Int, Int>`) and opposite directions for maze navigation.
- **Path Symbol and Connection States:** Utilizing characters (`pathSymbols`) to represent paths, each associated with a connection state.
- **Finding the Loop:** Implementing `findLoop` to locate the start ('S') and then `traverseLoop` for pathfinding.
- **Tail Recursive Loop Traversal:** Employing tail recursion in `traverseLoop` for efficient exploration.
- **Validation Checks:** Ensuring path validity and non-repetition with `isValidPosition` and `isRevisiting`.

This approach showcases Kotlin's efficiency in handling complex recursive operations.
#### Part 2

The second part of the challenge involves finding the amount tiles are enclosed by the loop. I've tried to reuse as much of my code from Part 1 as possible, 
but I coundn't find a way to do it without repeating some of the code. 
Since part of the code from part1 was adapted from a Python code, was way harder to come up with a solution that would work for both parts.


To solve parte2 I've used a algorithm adapted from the [Flood Fill](https://en.wikipedia.org/wiki/Flood_fill) algorithm to find the enclosed tiles.
