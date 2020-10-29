package db.dao;

import db.data.IGameResolverFactory;
import simulation.model.Game;
import simulation.model.GameResolver;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameResolverDao implements IGameResolverFactory {
    @Override
    public long addGameResolver(int leagueId,GameResolver gameResolver) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddGameResolver(?,?,?)");
            callDB.setInputParameterInt(1, leagueId);
            callDB.setInputParameterDouble(2, gameResolver.getRandomWinChance());


            callDB.setOutputParameterInt(3);
            callDB.execute();
            gameResolver.setId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return gameResolver.getId();
    }

    @Override
    public void loadGameResolverById(int id, GameResolver gameResolver) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadGameResolverById(?)");
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();
            if(rs!=null){
                gameResolver.setId(rs.getInt(1));
                gameResolver.setRandomWinChance(rs.getDouble(3));
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public void loadResolverByLeagueId(int leagueId, GameResolver gameResolver) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadGameResolverByLeagueId(?)");
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if(rs!=null){
                gameResolver.setId(rs.getInt(1));
                gameResolver.setRandomWinChance(rs.getDouble(3));
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
    }
}
