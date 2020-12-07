# CollegeFootballRATINGSystem

I used Glicko 2 algorithm as presented in the paper: http://www.glicko.net/glicko/glicko2.pdf.

## Modifications

I made some small modifications to the original Glicko 2 algorithm. Every "Player" is supposed to start with a rating of 1500 and Rating deviation of 350, but I changed the starting rating for P5 teams. P5 teams start at a rating of 1750 while non-P5 teams start at 1500, rating deviation starts at 350 for both.

This model only uses games from this season(2020) and the current top 25(as of 12/7) from the the model are:



| Team |  Rating | Rating Deviation | High End 95% Confidence Interval Rating | Low End 95% Confidence Interval Rating |
| :---: | :---: | :---: | :---: | :---: |
| Notre Dame | 2242.789 | 159.720 | 2562.223 | 1923.348 |
| Alabama | 2215.276 | 172.311 | 2559.898 | 1870.653 |
| Ohio State | 2161.648 | 191.887 | 2545.428 | 1777.874 |
| Clemson | 2140.521 | 150.733 | 2441.987 | 1839.056 | 
| Miami | 2083.436 | 150.283 | 2384.003 | 1782.869 | 
| Texas A&M | 2072.488 | 159.825 | 2402.139 | 1762.837 |
| Indiana | 2072.307 | 166.376 | 2405.06 | 1739.554 |
| Florida | 2041.116 | 153.487 | 2348.09 | 1734.141 |
| Coastal Carolina | 2039.373 | 146.809 | 2332.993 | 1745.755 |
