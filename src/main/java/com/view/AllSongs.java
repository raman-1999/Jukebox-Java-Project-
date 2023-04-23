package com.view;

import com.dao.DaoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllSongs extends DaoImpl
{
    public static void displaySongDetails()
    {
        System.out.println("------------------------------------------------------------------------------------------------------------\n"+
                "                            |+-+-+-+-+-+-+-+- Songs Menu -+-+-+-+-+-+-+-+|\n" +
                "------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-4s %-26s %-8s %20s %17s %29s","|ID|","|Song|" ,"|Artist|","|Genre|","|Album|","|Duration    |\n");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select * from songs");
            while(rs.next())
            {
                System.out.printf("|%-3s |%-25s |%-20s |%-16s |%-20s |%-10s  |\n", + rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
            }
            System.out.println("------------------------------------------------------------------------------------------------------------");
        }
        catch (SQLException e)
        {
            System.out.println("Database Error !!!");
        }
    }
    public static List allSongs()
    {
     List songs = new ArrayList<>();
     try
     {
         st = connectToDatabase().createStatement();
         rs = st.executeQuery("select * from songs");
         while (rs.next())
         {
             songs.add(rs.getInt(1));
         }
     }
     catch (Exception e)
     {
         System.out.println(e);
     }
     return songs;
    }
    public static List songById(int id)
    {
        List songById = new ArrayList<>();
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select songName from songs where songId = \""+id+"\";");
            while (rs.next())
            {
                songById.add(rs.getString(1));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return songById;
    }

}
