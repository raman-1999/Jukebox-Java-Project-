package com.view;

import com.dao.DaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AllAlbums extends DaoImpl
{
    static Statement st = null;
    static ResultSet rs = null;

    public static void displayAlbums()
    {
        System.out.println("-------------------------\n|\tAlbums\n-------------------------");
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select * from songs");
            while(rs.next())
            {
                System.out.println("|\t" + rs.getString(5));
            }
            System.out.println("-------------------------");

        }
        catch (SQLException e)
        {
            System.out.println("Database Error !!!");
        }
    }
    public List allAlbumsSongs(String name)
    {
        List albums = new ArrayList<>();
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select * from songs where albumName = \""+name+"\";");
            while (rs.next())
            {
                albums.add(rs.getString(4));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return albums;
    }
    public static List songByAlbum(String name)
    {
        List songByAlbumName = new ArrayList<>();
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select songName from songs where albumName = \""+name+"\";");
            while (rs.next())
            {
                songByAlbumName.add(rs.getString(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return songByAlbumName;
    }
}
