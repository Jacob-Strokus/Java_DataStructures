A decision tree is a flowchart-like structure in which each internal node represents a "test" on an attribute (e.g.
whether a coin flip comes up heads or tails), each branch represents the outcome of the test, and each leaf node
represents a class label (decision taken after computing all attributes). The paths from root to leaf represent
classification rules.

Builds a decision tree by reading a coma separated text file from a hard disk and use that
tree to give recommendation if the user can visit campus, get tested, or seek emergency medical service. When
the program runs, it will ask the user a series of questions and determines if the individual potentially has COVID19 or not. 
This decision will be based on the tree built from the txt file, which contains the level-order traversal
of a decision tree. Due to the unprecedented nature of the virus, there are still things we must learn about it. To
accommodate new information, our system is smart enough to learn from its environment by asking suggestion
from its user. Based on the accepted information, your program will update the tree and use that new information
in the next round of questions to the user. 
