package com.example.taskflow.fixture;

import com.example.taskflow.common.entity.Team;

public class TeamFixture {

    public static String DEFAULT_TEAMNAME = "테스트팀";
    public static String DEFAULT_DESCRIPTION = "팀 설먕";

    public static Team createTestTeam() {
        return new Team(DEFAULT_TEAMNAME, DEFAULT_DESCRIPTION);
    }
}
