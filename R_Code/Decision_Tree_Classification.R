#library(party)
library(partykit)

#https://www.youtube.com/watch?v=tU3Adlru1Ng
setwd("/Users/colinaslett/Desktop/SwimmingCSV")
training_data<-read.csv("training_data.csv", header = TRUE, stringsAsFactors = FALSE)
test_data <- read.csv("test_data.csv", header = TRUE, stringsAsFactors = FALSE)
training_data$VICTOR <- as.factor(training_data$VICTOR)
test_data$VICTOR <- as.factor(test_data$VICTOR)





tree <- ctree(VICTOR~.,data=training_data)
tree
plot(tree,gp = gpar(fontsize = 6),     # font size changed to 6
     inner_panel=node_inner,
     ip_args=list(
       abbreviate = TRUE, 
       id = FALSE))
p1 <- predict(tree,test_data)
(tab1 <- table(p1,test_data$VICTOR))
1-sum(diag(tab1))/sum(tab1)