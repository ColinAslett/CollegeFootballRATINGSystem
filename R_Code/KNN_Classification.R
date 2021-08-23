library(class)
#Video: https://www.youtube.com/watch?v=lDCWX6vCLFA
setwd("/Users/colinaslett/Desktop/SwimmingCSV")
test_data<-read.csv("test_data.csv", header = TRUE, stringsAsFactors = FALSE)
training_data<-read.csv("training_data.csv", header = TRUE, stringsAsFactors = FALSE)

test_data$VICTOR <- as.factor(test_data$VICTOR)
training_data$VICTOR <- as.factor(training_data$VICTOR)

data_norm <- function(x){((x-min(x))/(max(x)-min(x)))}
training_norm <- as.data.frame(lapply(training_data[,-1],data_norm))
test_norm <- as.data.frame(lapply(test_data[,-1],data_norm))
#Model
pred <- knn(training_norm,training_norm,training_data$VICTOR,k=70)
table(pred,training_data$VICTOR)

