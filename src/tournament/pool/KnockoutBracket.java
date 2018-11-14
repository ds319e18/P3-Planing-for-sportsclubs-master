package tournament.pool;

import tournament.Team;

import java.util.ArrayList;

public interface KnockoutBracket {
    KnockoutBracket createKnockoutBracket(GroupBracket groupBracket);
}
