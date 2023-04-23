package com.view;

import com.dao.DaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AllArtists extends DaoImpl
{
    static  Statement st = null;
    static ResultSet rs = null;

    public static void displayArtists()
    {
        System.out.println("-----------------------\n|\tArtists\n-----------------------");
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select * from songs");
            while(rs.next())
            {
                System.out.println("|\t" + rs.getString(3));
            }
            System.out.println("----------------------");
        }
        catch (SQLException e)
        {
            System.out.println("Database Error !!!");
        }
    }
    public List allArtistSongs(String name)
    {
        List artists = new ArrayList<>();
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select * from songs where artistName = \""+name+"\";");
            while (rs.next())
            {
                artists.add(rs.getString(3));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return artists;
    }
    public static List songByArtist(String name)
    {
        List songByArtist = new ArrayList<>();
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select songName from songs where artistName = \""+name+"\";");
            while (rs.next())
            {
                songByArtist.add(rs.getString(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return songByArtist;
    }
}
