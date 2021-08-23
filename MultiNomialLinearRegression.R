library(nnet)
library(ROCR)
#https://www.youtube.com/watch?v=S2rZp4L_nXo&list=PL34t5iLfZddvv-L5iFFpd_P1jy_7ElWMG
setwd("/Users/colinaslett/Desktop/SwimmingCSV")
test_data<-read.csv("test_data.csv", header = TRUE, stringsAsFactors = FALSE)
training_data<-read.csv("training_data.csv", header = TRUE, stringsAsFactors = FALSE)

test_data$VICTOR <- as.factor(test_data$VICTOR)
training_data$VICTOR <- as.factor(training_data$VICTOR)
a
mymodel <- multinom(VICTOR~.,data=training_data)
#summary(mymodel)

#Prediction with Confusion Matrix + Misclassification Error
set.seed(1234)
p <- predict(mymodel,training_data)
tab <- table(p,training_data$VICTOR)
tab
1-sum(diag(tab))/sum(tab)

#Cutoff Values and Accuracy: https://www.youtube.com/watch?v=ypO1DPEKYFo


