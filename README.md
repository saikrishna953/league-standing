# Get foot ball league standing API

```url
[GET] http://localhost:8080/leaguestanding/v1/leagues?leagueId=302&countryName=Spain&teamName=Real%20Madrid
```

#### Sample Response
```json
{
    "data": {
        "id": "302",
        "attributes": [
            {
                "country_name": "Spain",
                "league_id": "302",
                "league_name": "La Liga",
                "team_id": "76",
                "team_name": "Real Madrid",
                "overall_league_position": "1"
            }
        ],
        "links": [
            {
                "href": "http://localhost:8080/leaguestanding/v1/leagues?countryName=Spain&leagueId=302&teamName=Real%20Madrid",
                "rel": "self"
            }
        ]
    }
}
```

