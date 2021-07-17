# CollegeFootballRATINGSystem

This program interprets CSV Division 1 College Football game result data from https://collegefootballdata.com/ and uses 5 rating systems to rank teams from best to worst. The five rating systems currently used are ELO, Glicko 2, PageRank, BG, and HITS. 

## ELO
ELO is the most basic most well known for its use in chess and only uses the ELO of both players and the result of the game to determine each team’s new ELO. Each team's rating starts at 1500 at the beginning of the season, with a global K-value of 80-100.
## Glicko 2
Glikco 2 works similarly to ELO but includes a rating deviation, essentially a standard deviation, to each teams rating. The rating deviation for each team starts at 350 but as Glicko 2 gets more confident it will decrease and the main way that it gains confidence is through teams playing more games.
## PageRank
PageRank is a graph-based rating system, and unlike ELO and Glicko 2, the rating of a team is affected by other teams playing. PageRank was originally developed by Google to rank websites but can be used for sports and works by counting the quality of the wins over all the teams that a team has faced in a season. The quality of a win is determined by how well the other team has played that season, the better it has played the higher quality win it would give out.
## BG
BG was developed as an interpretation of BeatGraph, and similar to PageRank it is a graph-based rating system. BeatGraph is the only rating system that purposefully destroys part of the game data in an attempt to create a acyclic graph. In College Football this generally results in about 30-40% of game data being thrown away.
## HITS(Hyperlink-Induced Topic Search)
This algorithm was originally designed as a link analysis algorithm that rates web pages but I have modified the algorithm to rate college football teams. https://en.wikipedia.org/wiki/HITS_algorithm

Tables of rating system data from past and current College Football Seasons can be found at the link: https://colinaslett.github.io/
