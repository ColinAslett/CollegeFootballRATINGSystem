options(repos='http://cran.rstudio.org')
have.packages <- installed.packages()
cran.packages <- c('devtools','plotrix','randomForest','tree')
to.install <- setdiff(cran.packages, have.packages[,1])
if(length(to.install)>0) install.packages(to.install)

library(devtools)
if(!('reprtree' %in% installed.packages())){
  install_github('araastat/reprtree')
}
for(p in c(cran.packages, 'reprtree')) eval(substitute(library(pkg), list(pkg=p)))
library(tree)
library(ggplot2)
library(cowplot)
library(randomForest)
##https://www.youtube.com/watch?v=6EXPYzbfLCE
setwd("/Users/colinaslett/Desktop/SwimmingCSV")
data<-read.csv("College Football Past Season Analysis - PRE-SEASON RANDOM-FOREST.csv", header = TRUE, stringsAsFactors = FALSE)
predict_data <-read.csv("College Football Past Season Analysis - Prediction Sheet.csv",header = TRUE,stringsAsFactors = FALSE)

data$VICTOR <- as.factor(data$VICTOR)

##Setting seed to a specific number, and setting up model
set.seed(42)
##model <- randomForest(VICTOR~.,data=data,proximity=TRUE)
##model

oob.values <- vector(length=10)
for(i in 1:10) {
  temp.model <- randomForest(VICTOR~ ., data=data, mtry=i, ntree=1000)
  oob.values[i] <- temp.model$err.rate[nrow(temp.model$err.rate),1]
}
oob.values
## find the minimum error
min(oob.values)
## find the optimal value for mtry...
which(oob.values == min(oob.values))
## create a model for proximities using the best value for mtry
model <- randomForest(VICTOR~ ., data=data,ntree=1000, proximity=TRUE, mtry=which(oob.values == min(oob.values)))
##model <- randomForest(VICTOR~ ., data=data,ntree=1000, proximity=TRUE, mtry=1)

model


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

distance.matrix <- as.dist(1-model$proximity)

mds.stuff <- cmdscale(distance.matrix, eig=TRUE, x.ret=TRUE)

## calculate the percentage of variation that each MDS axis accounts for...
mds.var.per <- round(mds.stuff$eig/sum(mds.stuff$eig)*100, 1)

## now make a fancy looking plot that shows the MDS axes and the variation:
mds.values <- mds.stuff$points
mds.data <- data.frame(Sample=rownames(mds.values),
                       X=mds.values[,1],
                       Y=mds.values[,2],
                       Status=data$VICTOR)

ggplot(data=mds.data, aes(x=X, y=Y, label=Sample)) + 
  geom_text(aes(color=Status)) +
  theme_bw() +
  xlab(paste("MDS1 - ", mds.var.per[1], "%", sep="")) +
  ylab(paste("MDS2 - ", mds.var.per[2], "%", sep="")) +
  ggtitle("MDS plot using (1 - Random Forest Proximities)")
