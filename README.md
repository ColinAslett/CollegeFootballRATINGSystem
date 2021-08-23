# CollegeFootballRATINGSystem

## Part 1: NCAA D1 College Football Team Ratings
I have developed programs using Java to read the results of each NCAA D1 college football match during the season from collegefootballdata.com and then generate a rating and relative ranking of each team using five different rating systems. I have generated the rankings for the 2018-19, 2019-20, and 2020-21 seasons using the ratings systems described below. The results can be found at, https://colinaslett.github.io/.


This program extracts Division 1 College Football game results from a CSV file provided by https://collegefootballdata.com/ and uses 5 rating systems (Elo, Glicko 2, PageRank, BG, and HITS) to rank teams from best to worst. A basic description of the rating systems is below.

## Elo

Elo is a rating system that is well known for its use to measure the relative strength of chess players. As in chess, my program gives each team a rating of 1500 at the beginning of the season and assigns a global K value of 80-100 (the maximum possible adjustment per game). After each week of the season, the new ratings are re-calculated and used for subsequent matches. The advantage of this system is that it is easy to implement and track. The disadvantage is that all teams start with an equivalent rating when, in reality, some teams are clearly better than others. This can be mitigated to some degree by carrying over the ratings from the end of the previous season. For more information on Elo, see: https://en.wikipedia.org/wiki/Elo_rating_system

## Glicko 2

The Glicko 2 rating system works similarly to Elo but includes a rating deviation, essentially a standard deviation, for each team’s rating. This gives an indication of the confidence in the rating. In my program, the rating for each team starts at 1500 with a standard deviation of 350. The deviation decreases over time as more games are played. The team’s rating is shown as a range that spans two standard deviations to give a 95% confidence. For more information on Glicko 2, see https://en.wikipedia.org/wiki/Glicko_rating_system

## PageRank

PageRank is a graph-based rating system used by Google to rank search results based on the quantity and quality of links to a page. In my program, each team is linked to the teams it has played and its rating increases as the quality of those links increase. For example, if Team A wins against Team B in week 1 of the season and Team B then goes on to win all of its remaining games then the quality of the link to Team B will increase and the value of Team A’s initial win will go up. In this way, PageRank continuously adjusts the ratings of teams as the quality each team’s links change. A disadvantage of this approach is the existance of multiple loops within the graph that make it difficult to determine the correct relative quality of links (e.g. Team A beats Team B; Team C beats Team B; Team C beats Team A).  For more information on PageRank, see: https://en.wikipedia.org/wiki/PageRank

## BG

BG is my interpretation of the BeatGraphs rating system. It is similar to PageRank and it is also graph-based. The BeatGraphs rating system is different from others in that it purposefully destroys some of the team links that form a loop in an attempt to create an acyclic graph. This is meant to eliminate the problem with loops that I mentioned with PageRank. In College Football this may result in about 30-40% of game data being thrown away. For more on BeatGraphs, see: http://www.beatgraphs.com/GettingStarted.php

## HITS (Hyperlink-Induced Topic Search)

This is my interpretation of the hyperlink-induced topic search (HITS) a link analysis algorithm that was originally designed to rank webpages. HITS uses the concept of “hubs” and “authorities”. A team's rating is a combination of the authoritative value (based on the quality of the content) and the hub value (based on the value of the of links to other pages). For more information on HITs, see: https://en.wikipedia.org/wiki/HITS_algorithm

## Part 2: College Football Game Predictions
I use the R programming language and machine learning to build models based on classification algorithms described at cran.r-project.org  and summarized below. The algorithms classify (predict) games not yet played as either a home team victory or an away team victory. I use 4937 past games between season 2010 to 2020 for training and a different set of 670 past games over the same years for testing. More details can be found at, https://colinaslett.github.io/.

Random Forest: Random Forest is a machine learning method that constructs many decision trees at training time as described here . I use the R programming language to build a model based on the ‘randomForest’ package described here .

Support Vector Machines (SVM): A Support Vector Machine is a supervised machine learning algorithm. In my case, I use it for classification purposes where the SVMs attempt to find a hyperplane that best divides the dataset into two classes. More details on SVMs can be found here I use the R programming language to build a model based on the ‘e1071’ package described here .

Neural Net: An artificial Neural Network is a machine learning model inspired by the neural networks in the brain. In our case, the first hidden layer has four neurons and the second hidden layer has three neurons. More details on a Neural Net can be found here I use the R programming language to build a model based on the ‘neural net’ package described here .

Decision Tree: The decision tree is a predictive model. In my case, I build a classification tree based on our variables. Each node represents an "if-else" statement that can be followed to determine whether the decision tree predicts a home or away win. More details on decision trees can be found here I use the R programming language to build a model based on the ‘partykit’ package described here .

Naive Bayes: A Naive Bayes classifier is a probabilistic machine learning model that is using for classification tasks based on the Bayes theorem. More details on Naive Bayes can be found here . I use the R programming language to build a model based on the ‘naivebayes’ package described here . The figures below show a probability density plot for each variable used in the model.

k-Nearest Neighbor (k-NN): k-NN is a non-parametric classification method. Since we are solving a classification problem, we are using k-NN classification and the output is either "away" or "home". An object is classified based on the class most common among its "k" nearest neighbors where "k" is a small positive integer. More details can be found here I use the R programming language to build a model based on the ‘class’ package described here .
