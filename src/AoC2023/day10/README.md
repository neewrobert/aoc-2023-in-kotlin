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

#### Part 2
