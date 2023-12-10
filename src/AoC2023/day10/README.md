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

In Part 2 of the "Day 10: Pipe Maze" challenge, the objective was to calculate the number of tiles enclosed by the loop found in Part 1. 
To achieve this, I adapted a technique similar to the  [Flood Fill](https://en.wikipedia.org/wiki/Flood_fill) algorithm, a classical approach used in computer graphics and games. 
The main challenge was to integrate this new functionality while reusing as much code as possible from Part 1.

- **Reusing Grid Parsing:** Leveraged the same grid parsing strategy from Part 1 to create a structured `Array<CharArray>`.
- **Enhanced Loop Finding:** Modified `findLoopPath` to not only find the loop but also to track the path it takes. This required careful consideration of the directions and connections within the maze.
- **Enclosed Tile Calculation:**
  - After identifying the loop path, I implemented `countInsidePoints` to calculate the number of tiles enclosed by this loop.
  - The function iterates over the grid, marking the positions of the loop path and then uses a ray-casting-like method to determine whether a given tile is inside or outside the loop.
  - This approach involves tracking the transitions across the path boundary, toggling an 'inside' flag, and counting the tiles that fall inside the loop.

##### Challenges and Solutions
- **Code Reusability:** The main hurdle was integrating Part 2 with the existing code from Part 1 without excessive repetition. To address this, I focused on isolating the common functionalities (like grid parsing and pathfinding) and reusing them in both parts.
- **Algorithm Adaptation:** Adapting the [Flood Fill](https://en.wikipedia.org/wiki/Flood_fill) algorithm to the context of a maze required careful consideration of the maze's structure and the path's characteristics. By combining elements of ray-casting and boundary tracking, I was able to accurately count the enclosed tiles.

### Conclusion
Today's challenge was a very difficult, I think one of the most difficult so far. The good thing is that I've learned a lot from it. But my energy is running out, I'm not sure if I'll be able to finish the challenge this year. 
