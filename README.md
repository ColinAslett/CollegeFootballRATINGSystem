# CollegeFootballRATINGSystem

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
