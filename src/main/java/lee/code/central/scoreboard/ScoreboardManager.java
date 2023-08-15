package lee.code.central.scoreboard;

import lee.code.central.scoreboard.system.BoardData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardManager {

    private final ConcurrentHashMap<UUID, BoardData> playerScoreboardData = new ConcurrentHashMap<>();

    public void setPlayerBoard(UUID uuid, BoardData boardData) {
        playerScoreboardData.put(uuid, boardData);
    }

    public void removePlayerBoard(UUID uuid) {
        playerScoreboardData.remove(uuid);
    }

    public BoardData getPlayerBoard(UUID uuid) {
        return playerScoreboardData.get(uuid);
    }

    public boolean hasPlayerBoard(UUID uuid) {
        return playerScoreboardData.containsKey(uuid);
    }

    public List<BoardData> getAllBoards() {
        return new ArrayList<>(playerScoreboardData.values());
    }

}
