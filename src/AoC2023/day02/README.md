## --- Day 2: Cube Conundrum ---
### Overview
Day 2's challenge begins with a narrative setup involving an Elf and a water shortage issue.

In Part One, players analyze a series of games to identify possible game occurrences based on the number of cubes of different colors in a bag. The task is to calculate the sum of the IDs of games that could occur.

Part Two shifts focus, asking players to determine the minimum number of cubes of each color needed for each game. Here, the objective is to find the "power" of these minimum sets of cubes by multiplying the number of each color and then summing these powers across all games.
This part emphasizes problem-solving with a combination of color and quantity constraints.

### My Approach

#### Part 1
In Part 1, I approached the challenge by developing a function evaluateGameValidity to analyze game data. The function parses game sets to identify the number of cubes of each color.
The validity of each game is assessed based on specific conditions for the number of red, green, and blue cubes.
If a game meets the criteria, its ID is included in the final sum; otherwise, it's disregarded.
This part focuses on parsing and conditionally processing string data to determine game validity.

#### Part 2
For Part 2, I created calculateGameStrength to determine the minimum number of cubes needed for each game.
The function parses game data and calculates the maximum number of cubes required for each color across all sets.
It then computes the "power" of each game, defined as the product of the maximum number of red, green, and blue cubes.
This part emphasizes calculating minimum requirements and aggregating data from multiple subsets.