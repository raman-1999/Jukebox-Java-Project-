package com.view;

import com.dao.DaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AllGenres  extends DaoImpl
{
    public static void displayGenres()
    {
        int i = 1;
        String genre = "";
        ArrayList<String> genres = new ArrayList<>();
        System.out.println("---------------\n|\tGenres\n---------------");
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select genre from songs");
            while(rs.next())
            {
                genres.add(rs.getString(1));
            }
            HashSet<String> g = new HashSet<>();
            g.addAll(genres);
            for (String s:g)
            {
                genre = s;
                while (i <= g.size())
                {
                    System.out.println("|\t" + genre);
                    break;
                }
            }
            System.out.println("---------------");
        }
        catch (SQLException e)
        {
            System.out.println("Database Error !!!");
        }
    }
    public static List allGenreSongs(String name)
    {
        List genres = new ArrayList<>();
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select url from songs where genre = \""+name+"\";");
            while (rs.next())
            {
                genres.add(rs.getString(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return genres;
    }
    public static List songByGenre(String name)
    {
        List songByGenre = new ArrayList<>();
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select songName from songs where genre = \""+name+"\";");
            while (rs.next())
            {
                songByGenre.add(rs.getString(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return songByGenre;
    }

//    public static void main(String[] args) {
////        System.out.println(allGenreSongs("Rock"));
//    }
}
