#https://www.youtube.com/watch?v=lTMqXSSjCvk
library(neuralnet)
library(dpylr)
library(ggplot2)
library(psych)
library(caret)

setwd("/Users/colinaslett/Desktop/SwimmingCSV")
test_data<-read.csv("test_data.csv", header = TRUE, stringsAsFactors = FALSE)
training_data<-read.csv("training_data.csv", header = TRUE, stringsAsFactors = FALSE)

test_data$VICTOR <- as.factor(test_data$VICTOR)
training_data$VICTOR <- as.factor(training_data$VICTOR)

#pairs.panels(data[-1])

#model
set.seed(1234)
n <- neuralnet(VICTOR~.,data = training_data,
               hidden = c(4,3),
               err.fct = 'sse',
               linear.output = FALSE,
               threshold = .3)
plot(n)

output <- neuralnet::compute(n, training_data[,-1])
p1 <- output$net.result
pred <- ifelse(p1>0.5,1,0)
length(pred)
length(training_data$VICTOR)
tab1 <- table(pred,training_data$VICTOR)

nn.results <- neuralnet::compute(n,test_data[,-1])
results <- data.frame(test_data$VICTOR,prediction = nn.results$net.result)
pred <- ifelse(nn.results$net.result[,1]>0.5,1,0)
#nn.results$net.result[,1]
#training_data$VICTOR
table(pred,test_data$VICTOR) # 1 is AWAY and 0 is HOME, or at least im like 90% sure
                
                      
                      