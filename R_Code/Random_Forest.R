
library(ggplot2)
library(cowplot)
library(randomForest)
##https://www.youtube.com/watch?v=6EXPYzbfLCE
setwd("/Users/colinaslett/Desktop/SwimmingCSV")
training_data<-read.csv("training_data.csv", header = TRUE, stringsAsFactors = FALSE)
test_data <- read.csv("test_data.csv", header = TRUE, stringsAsFactors = FALSE)

training_data$VICTOR <- as.factor(training_data$VICTOR)
test_data$VICTOR <- as.factor(test_data$VICTOR)

##Setting seed to a specific number, and setting up model
set.seed(42)

oob.values <- vector(length=10)
for(i in 1:10) {
  temp.model <- randomForest(VICTOR~ ., data=training_data, mtry=i, ntree=1000,na.action=na.roughfix)
  oob.values[i] <- temp.model$err.rate[nrow(temp.model$err.rate),1]
}
oob.values
## find the minimum error
min(oob.values)
## find the optimal value for mtry...
which(oob.values == min(oob.values))
## create a model for proximities using the best value for mtry
model <- randomForest(VICTOR~ ., data=training_data,ntree=1000, importance=TRUE,proximity=TRUE,na.action=na.roughfix, mtry=which(oob.values == min(oob.values)))
##model <- randomForest(VICTOR~ ., data=data,ntree=1000, proximity=TRUE, mtry=1)

model

varImpPlot(model)

##This line of code is used for predictions
##y_pred = predict(model,newdata = predict_data)
##y_pred


##Printing out OOB error rate on a graph
oob.error.data <- data.frame(
  Trees=rep(1:nrow(model$err.rate), times=3),
  Type=rep(c("OOB", "HOME", "AWAY"), each=nrow(model$err.rate)),
  Error=c(model$err.rate[,"OOB"], 
          model$err.rate[,"HOME"], 
          model$err.rate[,"AWAY"]))

ggplot(data=oob.error.data, aes(x=Trees, y=Error)) +
  geom_line(aes(color=Type))

#Confusion Matrix for testing data
p1 <- predict(model,test_data)
(tab1 <- table(p1,test_data$VICTOR))
1-sum(diag(tab1))/sum(tab1)

