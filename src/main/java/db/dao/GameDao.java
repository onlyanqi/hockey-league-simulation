package db.dao;

import db.data.IGameFactory;
import simulation.model.Game;
import simulation.model.Training;
import util.DateUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDao implements IGameFactory {
    @Override
    public long addGame(int leagueId,Game game) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddGame(?,?,?,?,?,?,?)");
            callDB.setInputParameterInt(1, leagueId);
            callDB.setInputParameterDate(2, DateUtil.convertLocalDateToSQLDate(game.getDate()));
            callDB.setInputParameterString(3, game.getTeam1());
            callDB.setInputParameterString(4, game.getTeam2());
            callDB.setInputParameterBoolean(5,game.getPlayed());
            if(game.getWinner()==null){
                callDB.setInputParameterString(6,null);
            }else{
                callDB.setInputParameterString(6,game.getWinner().toString());
            }

            callDB.setOutputParameterInt(7);
            callDB.execute();
            game.setId(callDB.returnOutputParameterInt(7));

        } catch (SQLException sqlException) {
            throw sqlException;
        } catch (Exception exception){
            throw exception;
        }finally {
            callDB.closeConnection();
        }
        return game.getId();
    }

    @Override
    public void loadGameById(int id, Game game) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadGameById(?)");
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();
            if(rs!=null){
                game.setId(rs.getInt(1));
                game.setDate(rs.getDate(2).toLocalDate());
                game.setTeam1(rs.getString(3));
                game.setTeam2(rs.getString(4));
                game.setPlayed(rs.getBoolean(5));
                game.setWinner(Game.Result.TEAM1);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public List<Game> loadGamesByLeagueId(int leagueId) throws Exception {
        List<Game> gameList = null;
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadGameByLeagueId(?)");
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if(rs!=null){
                gameList = new ArrayList<>();
                while (rs.next()) {
                    Game game = new Game();
                    game.setId(rs.getInt(1));
                    game.setDate(rs.getDate(2).toLocalDate());
                    game.setTeam1(rs.getString(3));
                    game.setTeam2(rs.getString(4));
                    game.setPlayed(rs.getBoolean(5));
                    game.setWinner(Game.Result.valueOf(rs.getString(6)));
                    gameList.add(game);
                }
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return gameList;
    }
}
