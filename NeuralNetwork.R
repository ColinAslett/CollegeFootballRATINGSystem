#https://www.youtube.com/watch?v=lTMqXSSjCvk
library(neuralnet)
require(neuralnet)

setwd("/Users/colinaslett/Desktop/SwimmingCSV")
data<-read.csv("College Football Past Season Analysis - PRE-SEASON RANDOM-FOREST.csv", header = TRUE, stringsAsFactors = FALSE)

data$VICTOR <- as.factor(data$VICTOR)

#Good Info on how to determine number of hidden layers and 
#neurons
set.seed(123)
nn = neuralnet(VICTOR~.,data=data,hidden=5,err.fct="sse",
               linear.output = FALSE)
plot(nn)
##Column 1 should be for AWAY, Column 2 for HOME
nn$net.result[[1]]
