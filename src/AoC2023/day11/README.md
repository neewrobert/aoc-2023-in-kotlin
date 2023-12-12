## --- Day 11: Cosmic Expansion ---

### Overview
In the "Cosmic Expansion" project, the goal was to analyze a cosmic map, represented as a grid of galaxies and empty space. 
The task was to calculate the total path distance between galaxies after applying an expansion factor to the universe, causing galaxies to drift apart.

### My Approach

#### Part 1
In the first part of the challenge, I focused on parsing the cosmic map. 
This map was represented as a list of strings, where each character corresponded to either a galaxy ('#') or empty space ('.'). 
My primary objective was to accurately identify the location of each galaxy and store these in a structured format. 
This was accomplished by the findGalaxies function, which iterated over each cell of the map to construct a list of Galaxy objects.

#### Part 2
The second part was more challenging. It involved expanding the universe by a given expansion factor. 
This meant recalculating the positions of galaxies as they moved apart. 
I achieved this by identifying empty rows and columns and then applying the expansion logic to adjust the coordinates of each galaxy. 
This required careful consideration of the map's boundaries and ensuring that the expansion was applied uniformly.

##### Challenges and Solutions
 - **Parsing the Map:** Initially, parsing the map and identifying galaxies was cumbersome. 
   I simplified this by using `forEachIndexed` for efficient iteration with indices.
 - **Expanding the Universe:** Calculating the new positions of galaxies post-expansion was complex. 
   I resolved this by breaking down the problem: first identifying empty rows and columns, then applying expansion to each galaxy.
 - **Efficient Pairing of Galaxies:** Calculating the path distances between all pairs of galaxies was initially inefficient. 
   I optimized this using the `allPairs extension function on the list, leveraging Kotlin's functional programming capabilities.

### Conclusion
It was a great exercise in problem-solving and applying Kotlin's idiomatic features. 
It reinforced the importance of breaking down complex problems into smaller, manageable functions. 
I learned how to efficiently manipulate data structures and iterate over them using Kotlin's standard library functions. 
Moreover, the challenge of expanding the universe in a grid-like structure was a unique and interesting problem, pushing me to think about spatial relationships and transformations.

