package application;

import core.Space;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import application.MainController;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author NaDa DaHaB
 */
public class Database {

    static Connection con;
    static Statement stmt;
    static ResultSet rss;
    static String queryString;
    static int score;
    static HomeSceneController Home;
    static MainController MC;

    public Database() {

        try {
            Home = new HomeSceneController();
            Class.forName("com.mysql.jdbc.Driver");
            String myUrl = "jdbc:mysql://localhost/tictac";
            con = DriverManager.getConnection(myUrl, "root", "");
            stmt = con.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/** 
 * get player id
 */
    public int getPlayerId(String Name) {
        ResultSet rs;
        int player_id = 0;

        try {

            String player1 = new String("select id from tictac.player1 where name ='" + Name + "'");

            rs = stmt.executeQuery(player1);
            if (rs.next()) {
                player_id = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return player_id;
    }
/** 
*set player name
*/
    public void playerName(String Name) {

        try {
            PreparedStatement s;

            s = con.prepareStatement("insert into tictac.player1(name) values(?)");
            try {

                s.setString(1, Name);

            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }

            s.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
/** 
 * get player1 name
 */
    public String getPlayer1Name() {

        return HomeSceneController.temp1;       // player1 multi

    }
/** 
 * get player2 name
 */
    public String getPlayer2Name() {         // player2 multi

        return HomeSceneController.temp2;

    }
/** 
 * get player name
 */
    public String getPlayer3Name() {     //single

        return HomeSceneController.temp3;

    }
/** 
 * get player name
 */
    public String getPlayernName() {     //network

        return HomeSceneController.tempN;

    }
/** 
 * set score
 */
    public void setXScore() {
        try {
            String sql;
            score = 0;
            PreparedStatement sql1;
            sql = new String("select score from tictac.game where id=" + getGameID());
            rss = stmt.executeQuery(sql);
            if (rss.wasNull()) {
                score = 5;
                sql1 = con.prepareStatement("update tictac.game set score=? where id=?");
                sql1.setInt(1, score);
                sql1.setInt(2, getGameID());
                sql1.executeUpdate();
            } else {
                score += 5;
                sql1 = con.prepareStatement("update tictac.game set score=? where id=?");
                sql1.setInt(1, score);
                sql1.setInt(2, getGameID());
                sql1.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
/** 
 * set game type (single - multi - networked)
 */
    public void setGameType(String gameType) {

        PreparedStatement s2;

        if (gameType == "multi") {

            try {
                String name1 = getPlayer1Name();
                String name2 = getPlayer2Name();

                int player1_id = getPlayerId(name1);
                int player2_id = getPlayerId(name2);

                s2 = con.prepareStatement("insert into tictac.game(player1_id , player2_id,gametype) values(?,?,?)");
                s2.setInt(1, player1_id);
                s2.setInt(2, player2_id);
                s2.setString(3, gameType);
                s2.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (gameType == "single") {
            try {
                String name1 = getPlayer3Name();
                int player1_id = getPlayerId(name1);
                s2 = con.prepareStatement("insert into tictac.game(player1_id,gametype) values(?,?)");
                s2.setInt(1, player1_id);

                s2.setString(2, gameType);
                s2.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (gameType == "networked") {
            try {
                String name1 = getPlayernName();
                int player1_id = getPlayerId(name1);
                s2 = con.prepareStatement("insert into tictac.game(player1_id,gametype) values(?,?)");
                s2.setInt(1, player1_id);

                s2.setString(2, gameType);
                s2.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
/** 
 * get game id
 */
    public int getGameID() {

        int gameID = 0;
        try {

            String s3;
            s3 = new String("SELECT id FROM tictac.game ORDER BY ID DESC LIMIT 1 ");

            rss = stmt.executeQuery(s3);
            if (rss.next()) {
                gameID = rss.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gameID;
    }
/** 
 * save moves for game
 */
    public void movesGame(String symbal, int crow, int ccol) {
        PreparedStatement mm;

        try {

            mm = con.prepareStatement("insert into tictac.moves(morder,game_id,symbal,crow,ccol) values(?,?,?,?,?)");
            mm.setInt(1, MainController.morder++);
            mm.setInt(2, getGameID());
            mm.setString(3, symbal);
            mm.setInt(4, crow);
            mm.setInt(5, ccol);
            mm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/** 
 * save moves for game in networked
 */
    public void movesNGame(String symbal, int crow, int ccol) {
        PreparedStatement mm;

        try {

            mm = con.prepareStatement("insert into tictac.moves(morder,game_id,symbal,crow,ccol) values(?,?,?,?,?)");
            mm.setInt(1, TicTacToeClient.mNorder++);
            mm.setInt(2, getGameID());
            mm.setString(3, symbal);
            mm.setInt(4, crow);
            mm.setInt(5, ccol);
            mm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/** 
 * get symbal
 */
    public Space SelectMovesSymbal(int game_id, int morder) {

        String MovesSymbal = null;
        Space cc = null;
        String query = new String("select symbal from tictac.moves where game_id=" + game_id + " and morder=" + morder);
        try {

            rss = stmt.executeQuery(query);

            if (rss.next()) {
                MovesSymbal = rss.getString(1);

            
            if (MovesSymbal.equals("x")) {
                cc = Space.X;

            } else {
                cc = Space.O;
            }
            } 
        }
        catch (Exception ex) {
          //  System.out.println("hhhh");
           Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cc;
    }
/** 
 * get row for play
 */
    public int SelectMovesX(int game_id, int morder) {
        ResultSet rs;
        int row = 0;

        String query = new String("select crow from tictac.moves where game_id=" + game_id + " and morder=" + morder);
        try {
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }
/** 
 * get column for play
 */
    public int SelectMovesY(int game_id, int morder) {
        ResultSet rs;
        int col = 0;

        String query = new String("select ccol from tictac.moves where game_id=" + game_id + " and morder=" + morder);
        try {
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                col = rs.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return col;
    }
/** 
 * get score
 */
    public int getScore() {
        int scoreX = 0;
        ResultSet rs;
        try {

            String query = new String(" select score from game where id=" + getGameID());

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                scoreX = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scoreX;
    }
}
