Feature: getLeagueStanding API

  Scenario: Verifying health check of getLeagueStanding API
    When client sends a request to league standing microservice for health check
    Then client should receive response with http status as 200
    And league standing microservice should return health check state as 'UP'

  Scenario: Successful response
    When client submits getLeagueStanding API with '302', 'Spain' and 'Real Madrid'
    Then client should receive response with http status as 200
    And getLeagueStanding API should respond with corresponding overall league positions for the given input

  Scenario Outline: Error Response
    When client submits getLeagueStanding API with '30', 'Spain' and 'Real Madrid'
    Then getLeagueStanding API returns an error response back to client with status as '<status>', code as '<code>', title as '<title>', detail as '<detail>'

    Examples:
      | status | code | title               | detail      |
      | 404    | 101  | League id not found | leagueId=30 |
