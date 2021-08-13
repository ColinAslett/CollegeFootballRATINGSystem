library(party)
#https://www.youtube.com/watch?v=tU3Adlru1Ng
setwd("/Users/colinaslett/Desktop/SwimmingCSV")
data<-read.csv("College Football Past Season Analysis - PRE-SEASON RANDOM-FOREST.csv", header = TRUE, stringsAsFactors = FALSE)

data$VICTOR <- as.factor(data$VICTOR)

tree <- ctree(VICTOR~.,data=data)
tree
plot(tree)
