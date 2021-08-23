library(party)
#https://www.youtube.com/watch?v=pS5gXENd3a4
setwd("/Users/colinaslett/Desktop/SwimmingCSV")
training_data<-read.csv("training_data.csv", header = TRUE, stringsAsFactors = FALSE)
test_data <- read.csv("test_data.csv", header = TRUE, stringsAsFactors = FALSE)
training_data$VICTOR <- as.factor(training_data$VICTOR)
test_data$VICTOR <- as.factor(test_data$VICTOR)

library(e1071)
#Cost seems to have a big impact, epsilon not so much???, I don't want to run
# this for like 2 hours to find out
mymodel <- svm(VICTOR~.,data=training_data,kernel="radial",cost=128,epsilon = 0.6)
summary(mymodel)
#tuning
#set.seed(123)
#tmodel <- tune(svm,VICTOR~.,data=data,ranges= list(epsilon=seq(0,1,0.2),
                                         #cost = 2^(1:3)))
#plot(tmodel)
#summary(tmodel)
#summary(mymodel)

#plot(mymodel,data=training_data,H_W_M_A_W~RR_DIFF)

pred <- predict(mymodel,test_data)
tab <- table(Predicted = pred,actual = test_data$VICTOR)
tab
1-sum(diag(tab))/sum(tab)