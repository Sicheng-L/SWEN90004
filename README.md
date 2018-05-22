# WealthDistribution

A java implementation to mimic NetLogo Wealth Distribution Model.

Please refer to NetLogo documents to find the detailed information.

For the model itself:
```
Wilensky, U. (1998). NetLogo Wealth Distribution model. 
http://ccl.northwestern.edu/netlogo/models/WealthDistribution. 
Center for Connected Learning and Computer-Based Modeling, 
Northwestern University, Evanston, IL.
```

NetLogo software:
```
Wilensky, U. (1999). NetLogo. http://ccl.northwestern.edu/netlogo/. 
Center for Connected Learning and Computer-Based Modeling, 
Northwestern University, Evanston, IL.
```

## Getting Started

Compile the project
```
javac main.java
```

Run the project
```
java main
```

Note:
To investigate 
Params.java: Constants used in the model
(Change the corresponding field for certain experiments)

* NUM_PEOPLE: int 
* METABOLISM_MAX: int 
* PERCENT_BEST_LAND: double 
* PERCENT_INHERIT: double - extension

For extension:
-- remove comment Controller.java line 155
-- add comment Controller.java line 152-154


Now the output should look like
 
```
Tick20: Poor:209, Middle:37, Rich:4.
Tick50: Poor:193, Middle:46, Rich:11.
Tick80: Poor:222, Middle:25, Rich:3.
```

## Authors

```
Sicheng Liu
sichengl4@student.unimelb.edu.au
```

```
Jiacheng Sun
jiachengs@student.unimelb.edu.au
```
