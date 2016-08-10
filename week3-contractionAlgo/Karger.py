import random
import copy
 
filename = "input.txt"
 
def makeMaps(filename):
    file1 = open(filename)
    graph = {}
    count = 0
    print "Loading from",filename
    for line in file1:
        node = int(line.split()[0])
        edges = []
        for edge in line.split()[1:]:
            edges.append(int(edge))
        graph[node] = edges
        count += 1
    print len(graph), "vertices in dictionary."
    file1.close()
    return graph
 
graph = makeMaps(filename)
 
cuts = []
 
def karger(graph):
    while len(graph) > 2:
        removed = 0
        added = 0
        start = random.choice(graph.keys())
        finish = random.choice(graph[start])
 
    ## Adding the edges from the absorbed node:
        for edge in graph[finish]:
            if edge != start:# this stops us from making a self-loop
                graph[start].append(edge)
               
    ## Deleting the references to the absorbed node and changing them to the source node:
        for edge1 in graph[finish]:
            graph[edge1].remove(finish)
            if edge1 != start:# this stops us from re-adding all the edges in start.
                graph[edge1].append(start)
        del graph[finish]
 
    ## Calculating and recording the mincut
    mincut = len(graph[graph.keys()[0]])
    cuts.append(mincut)
   
for i in range(1,100):
    graph1 = copy.deepcopy(graph)
    karger(graph1)
print "Mincut is", min(cuts)