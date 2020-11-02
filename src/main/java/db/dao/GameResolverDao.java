package db.dao;

import db.data.IGameResolverFactory;
import simulation.model.GameResolver;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameResolverDao extends DBExceptionLog implements IGameResolverFactory {
    @Override
    public long addGameResolver(int leagueId, GameResolver gameResolver) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "AddGameResolver(?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, leagueId);
            callDB.setInputParameterDouble(2, gameResolver.getRandomWinChance());

            callDB.setOutputParameterInt(3);
            callDB.execute();
            gameResolver.setId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException) {
            printLog("GameResolverDao: addGameResolver: SQLException: "+sqlException);
            throw sqlException;
        } finally {
            if(getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return gameResolver.getId();
    }

    @Override
    public void loadGameResolverById(int id, GameResolver gameResolver) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "LoadGameResolverById(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();
            if (getValidation().isNotNull(rs)) {
                gameResolver.setId(rs.getInt(1));
                gameResolver.setRandomWinChance(rs.getDouble(3));
            }
        } catch (SQLException sqlException) {
            printLog("GameResolverDao: loadGameResolverById: SQLException: "+sqlException);
            throw sqlException;
        } finally {
            if(getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }

    @Override
    public void loadResolverByLeagueId(int leagueId, GameResolver gameResolver) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "LoadGameResolverByLeagueId(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if (getValidation().isNotNull(rs)) {
                gameResolver.setId(rs.getInt(1));
                gameResolver.setRandomWinChance(rs.getDouble(3));
            }
        } catch (SQLException sqlException) {
            printLog("GameResolverDao: loadResolverByLeagueId: SQLException: "+sqlException);
            throw sqlException;
        } finally {
            if(getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }
}
