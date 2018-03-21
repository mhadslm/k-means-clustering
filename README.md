# k-means-clustering
K means clustering algorithm was implemented as a java-based software capable of clustering or dividing a specific numeric data set to a specified clusters or groups and rewriting them back as clusters or groups to a predetermined output file.
The implemented software can read comma delimited dataset files in order to read the instances and load them to the machine memory to be able to start the clustering process.
The hole implementation was carried using java programming language through netbeans development environment. 
The implementation process can be divided into parts as follow:


1.	Input/output read/write:
In this part java build in classes specifically the java.io package were used to read the selected dataset file. 
The loaded file will be parsed using java build in iterates and the data will be loaded to the machine's main memory so the algorithm implementation and clustering operation in the  next step can be started.
The setList method is the main implementation for this subpart as shown in figure 1 and it will be called directly as soon as the kmeans class is constructed.


2.	Algorithm implementation
Every step of  the K means algorithm was implemented in this part. As K means is an iterative algorithm so all these parts will be implemented in an infinite loop until the algorithm reach its final state after that the loop will be terminated. This infinite loop was implemented in the calculate method as shown in figure 3. 


3.	Graphical presentation
In this part a  java based 2D panel was used as an XY coordinates panel to plot the clustered data.
