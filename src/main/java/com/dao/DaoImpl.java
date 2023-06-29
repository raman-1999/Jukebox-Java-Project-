package com.dao;

import com.view.AllAlbums;
import com.view.AllArtists;
import com.view.AllGenres;
import com.view.AllSongs;
import com.dao.SongOperations;
import jdk.jfr.Enabled;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.List;
import javax.sound.sampled.*;
import javax.swing.*;

public class DaoImpl
{
    public static Statement st = null;
    public static ResultSet rs = null;
    static Scanner sc =new Scanner(System.in);
    static Connection connect = null;
    static PreparedStatement ps = null;
    static File songUrl = null;
    static AudioInputStream song = null;
    static List<Integer> idList = null;
    static int count = 1;
    static int id=0;
    static int choice1;
    static int choice2;
//    static int choice3;
    static String userChoice1 = "";
    static String name;
    static String name1 ="";
    static String name2 ="";
    static AllSongs songs = new AllSongs();
    static SongOperations play = new SongOperations();
    static SongOperations byName = new SongOperations();
    static SongOperations inOrder = new SongOperations();

//    Function for database connection.
    public static Connection connectToDatabase()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","root");
//            System.out.println("Connection Established");
        }
        catch (SQLException e)
        {
            System.out.println("Failed to connect to Database");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Failed to connect to Database");
        }
        return connect;
    }
//    Display Menu function
    public static void displayMenu()
    {
        System.out.println("---------------------------------------Menu-------------------------------------");
        System.out.println("|     1 --> All Songs                                                           |\n" +
                "---------------------------------------------------------------------------------\n" +
                "|     2 --> Artists                                                             |\n" +
                        "---------------------------------------------------------------------------------\n" +
                "|     3 --> Genres                                                              |\n" +
                        "---------------------------------------------------------------------------------\n" +
                "|     4 --> Albums                                                              |\n" +
                        "---------------------------------------------------------------------------------\n" +
                "|     5 --> Create a playlist                                                   |\n" +
                        "---------------------------------------------------------------------------------\n" +
                "|     6 --> Delete a playlist                                                   |\n" +
                        "---------------------------------------------------------------------------------\n" +
                "|     7 --> View Playlists                                                      |\n" +
                        "---------------------------------------------------------------------------------\n" +
                "|     8 --> Exit Jukebox                                                        |");
        System.out.println("---------------------------------------------------------------------------------");
        menuOperations();
    }
//    Menu options with operations
    public static void menuOperations()
    {
        try {
                System.out.print("");
                System.out.print("Enter your choice --> ");
                choice1 = sc.nextInt();
                switch (choice1) {
                    case 1:
                            do
                            {
                                try {
//                                    displaying song list
                                songs.displaySongDetails();
                                System.out.print("1 --> Choose a particular song\n2 --> Search song by name\n3 --> Play songs from the beginning of the list \n4 --> Back to Menu\nEnter your choice --> ");
                                choice2 = sc.nextInt();
//                                performing action according to user input
                                switch (choice2)
                                {
                                    case 1:
                                        try
                                        {
                                            System.out.print("Enter ID of the song you want to play --> ");
                                            id = sc.nextInt();
//                                            performing action according to user input
                                            if(songs.allSongs().contains(id))
                                                play.playSong(id);
                                            else
                                            {
                                                do
                                                {
                                                    System.out.print("Song not found !\n");
                                                    System.out.print("Enter ID again OR Enter any letter to go back --> ");
                                                    id = sc.nextInt();
                                                    if(songs.allSongs().contains(id))
//                                                        callingRequiredFunction
                                                        play.playSong(id);
                                                }while (!songs.allSongs().contains(id));
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("Entry Invalid ! Going Back...");
                                            sc.nextLine();
                                            break;
                                        }
                                        break;
                                    case 2:
                                        String name;
                                        System.out.print("Enter song's name --> ");
                                        sc.nextLine();
                                        name = sc.nextLine();
//                                      callingRequiredFunction
                                        byName.playSong(name);
                                        break;
                                    case 3:
//                                        callingRequiredFunction
                                        inOrder.playInOrder();
                                        break;
                                    case 4:
//                                        callingRequiredFunction
                                        displayMenu();
                                        break;
                                    default:
                                        System.out.println("INVALID CHOICE !!! \nEnter a 'NUMBER' from the list of options to proceed.\n");
                                        break;
                                }
                                } catch (InputMismatchException e) {
                                    System.out.println("Entry Invalid !!! \nEnter a 'NUMBER' from the list of options to proceed.");
                                    sc.nextLine();
                                }
                                catch (Exception e)
                                {
                                    System.out.println(e);
                                    sc.nextLine();
                                }
                            } while (choice2 != 1 || choice2 != 2 || choice2 != 3 || choice2 != 4);
                            break;
                    case 2:
                            do
                            {
                                try
                                {
//                                    displaying all artists
                                    AllArtists.displayArtists();
                                System.out.print("1 --> To choose Artist\n2 --> Back to Menu\nEnter your choice --> ");
                                choice2 = sc.nextInt();
                                sc.nextLine();
                                if (choice2 == 1) {
                                    System.out.print("Enter name of the Artist --> ");
                                    name1 = sc.nextLine();
                                    name1 = name1.toLowerCase();
                                    st = connectToDatabase().createStatement();
                                    rs = st.executeQuery("select * from songs where artistName = \"" + name1 + "\";");
                                    if (!rs.next()) {
                                        System.out.println("Artist not found ! Going Back...");
                                    } else {
                                        count = 1;
                                        idList = new ArrayList<>();
                                        idList.add(rs.getInt(1));
                                        name2 = rs.getString(3);
//                                        displaying info for chosen artist in tabular format
                                        System.out.println("Songs of " + name2+"\n----------------------------------");
                                        System.out.printf("%-2s %-5s %-5s","|Sr No.","|ID","|Song\n----------------------------------\n");
                                        System.out.println("|" + count + "\t\t|" + rs.getInt(1) + "\t  |" + rs.getString(2));
                                        while (rs.next()) {
                                            count++;
                                            System.out.println("|" + count + "\t\t|" + rs.getInt(1) + "\t  |" + rs.getString(2));
                                            idList.add(rs.getInt(1));
                                        }
                                        System.out.print("Enter ID of the song you want to play --> ");
                                        id = sc.nextInt();
//                                        calling required function
                                        if (idList.contains(id))
                                        {
                                            for(Integer i : idList)
                                            {
                                                songByAGA(i);
                                            }
                                            System.out.println("No more songs to play for this artist");
                                        }
                                        else
                                            System.out.println("Song not found ! Going Back...");
                                    }
                                }
                                else if (choice2 == 2)
                                    displayMenu();
                                else
                                    System.out.print("INVALID CHOICE !!! \nEnter a 'NUMBER' from the list of options to proceed.\n");
                            } catch (InputMismatchException e) {
                            System.out.println("Entry Invalid ! \nEnter a 'NUMBER' from the list of options to proceed.");
                            sc.nextLine();
                                }
                                catch (Exception e)
                                {
                                    System.out.println(e);
                                    sc.nextLine();
                                }
                            }while (choice2 != 1 || choice2 != 2);
                        break;
                    case 3:
                            do {
                                try {
//                                    displaying all genres
                                AllGenres.displayGenres();
                                System.out.print("1 --> To choose Genre\n2 --> Back to Menu\nEnter your choice --> ");
                                choice2 = sc.nextInt();
                                if (choice2 == 1) {
                                    System.out.print("Enter name of the Genre --> ");
                                    sc.nextLine();
                                    name1 = sc.nextLine();
                                    name1 = name1.toLowerCase();
                                    st = connectToDatabase().createStatement();
                                    rs = st.executeQuery("select * from songs where genre = \"" + name1 + "\";");
                                    if (!rs.next()) {
                                        System.out.println("Genre not found !");
                                    } else {
                                        count = 1;
                                        idList = new ArrayList<>();
                                        idList.add(rs.getInt(1));
                                        name2 = rs.getString(4);
//                                        displaying info of genre chosen in tabular format
                                        System.out.println(name2 + " Songs");
                                        System.out.println("Songs of " + name2+"\n----------------------------------");
                                        System.out.printf("%-2s %-5s %-5s","|Sr No.","|ID","|Song\n----------------------------------\n");
                                        System.out.println("|" + count + "\t\t|" + rs.getInt(1) + "\t  |" + rs.getString(2));
                                        while (rs.next()) {
                                            count++;
                                            System.out.println("|" + count + "\t\t|" + rs.getInt(1) + "\t  |" + rs.getString(2));
                                            idList.add(rs.getInt(1));
                                        }
                                        System.out.print("Enter ID of the song you want to play --> ");
                                        id = sc.nextInt();
                                        if (idList.contains(id))
                                        {
                                        for(Integer i : idList)
                                        {
                                            songByAGA(i);
                                        }
                                        System.out.println("No more songs to play for this genre");
                                        }
                                        else
                                            System.out.println("Song not found ! Going Back...");
                                    }
                                } else if (choice2 == 2)
                                    displayMenu();
                                else
                                    System.out.print("INVALID CHOICE !!! \nEnter a 'NUMBER' from the list of options to proceed.\n");
                            } catch (InputMismatchException e) {
                                    System.out.println("Entry Invalid !\nEnter a 'NUMBER' from the list of options to proceed.");
                                    sc.nextLine();
                                }
                                catch (Exception e)
                                {
                                    System.out.println(e);
                                    sc.nextLine();
                                }
                        } while (choice2 != 1 || choice2 != 2);
                        break;
                    case 4:
                            do {
                                try {
                                    AllAlbums.displayAlbums();
                                    System.out.print("1 --> To choose Album\n2 --> Back to Menu\nEnter your choice --> ");
                                    choice2 = sc.nextInt();
                                    if (choice2 == 1) {
                                        System.out.print("Enter name of the Album --> ");
                                        sc.nextLine();
                                        name1 = sc.nextLine();
                                        name1 = name1.toLowerCase();
                                        st = connectToDatabase().createStatement();
                                        rs = st.executeQuery("select * from songs where albumName = \"" + name1 + "\";");
                                        if (!rs.next()) {
                                            System.out.println("Album not found !");
                                        } else {
                                            count = 1;
                                            idList = new ArrayList<>();
//                                    while (rs.next()) {
                                            idList.add(rs.getInt(1));
                                            name2 = rs.getString(5);
                                            System.out.println("Songs from the album " + name2+"\n----------------------------------");
                                            System.out.printf("%-2s %-5s %-5s","|Sr No.","|ID","|Song\n----------------------------------\n");
                                            System.out.println("|" + count + "\t\t|" + rs.getInt(1) + "\t  |" + rs.getString(2));
                                            while (rs.next()) {
                                                count++;
                                                System.out.println("|" + count + "\t\t|" + rs.getInt(1) + "\t  |" + rs.getString(2));
                                                idList.add(rs.getInt(1));
                                            }
                                            System.out.print("Enter ID of the song you want to play --> ");
                                            id = sc.nextInt();
                                            if (idList.contains(id))
                                            {
                                                for(Integer i : idList)
                                                {
                                                    songByAGA(i);
                                                }
                                                System.out.println("No more songs to play for this album");
                                            }
                                            else
                                                System.out.println("Song not found ! Going Back...");
                                        }
                                    } else if (choice2 == 2)
                                        displayMenu();
                                    else
                                        System.out.print("INVALID CHOICE !!! \nEnter a 'NUMBER' from the list of options to proceed.\n");
                                }catch (InputMismatchException e) {
                                        System.out.println("Entry Invalid !\nEnter a 'NUMBER' from the list of options to proceed.");
                                        sc.nextLine();
                                    }
                                catch (Exception e)
                                {
                                    System.out.println(e);
                                    sc.nextLine();
                                }
                            } while (choice2 != 1 || choice2 != 2);
                        break;
                    case 5:
                        createPlaylist();
                        break;
                    case 6:
                        choosePlaylistToDelete();
                        try {
                            System.out.print("Enter name of the playlist you want to delete --> ");
                            name = sc.next();
                            st = connectToDatabase().createStatement();
                            st.executeUpdate("drop table " + name + ";");
                            System.out.println("Playlist '" + name + "' deleted.");
                            displayMenu();
                        } catch (SQLException e) {
                            System.out.println("Playlist not found !!");
                            sc.nextLine();
                            displayMenu();
                        }catch (Exception e) {
                            System.out.println(e);
                        }
                        break;
                    case 7:
                        choosePlaylist();
                        try {
                            System.out.print("Enter name of the playlist you chose --> ");
                            name = sc.next();
                            st = connectToDatabase().createStatement();
                            rs = st.executeQuery("select * from " + name);
                            if(!rs.next())
                            {
                                System.out.println("Playlist is empty !!");
                            }
                            else
                            {
                                System.out.println("---------------------------------------------------\n|\t Songs in the play-list '"+name+"'\n---------------------------------------------------");
                                System.out.printf("%-2s %-25s %-5s","|Sr No.","|Song","|Album\n---------------------------------------------------\n");
                                count = 1;
                                System.out.printf("|%-6s |%-24s |%-5s\n",count, rs.getString(2), rs.getString(3));
                                while (rs.next()) {
                                    count++;
                                    System.out.printf("|%-6s |%-24s |%-5s\n",count, rs.getString(2), rs.getString(3));

                                }
                                System.out.println("---------------------------------------------------");
                                System.out.println("Playlist ready to play !!");
                                playInOrderFromPlaylist(name);
                            }
                        }catch (SQLException e) {
                            System.out.println("Playlist not found !!");
                            sc.nextLine();
                            displayMenu();
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        break;
                    case 8:
                        System.out.println("\n                                ***** THANKS FOR VIBING *****");
                        System.exit(1);
                    default:
                        System.out.println("Invalid Choice");
                        displayMenu();
                        break;
                }
        }
        catch (Exception e)
        {
            System.out.println("Entry Invalid ... Enter a 'NUMBER' from the list of menu options to proceed.\n");
            sc.nextLine();
            displayMenu();
        }
    }

    public static void createPlaylist()
    {
        System.out.print("Enter name of the playlist --> ");
        String name = sc.next();
        int song = 0;
        AllSongs obj = new AllSongs();
        try {
            st = connectToDatabase().createStatement();
            st.executeUpdate("create table "+name+"(playlistId int, songName varchar(255) , artistName varchar(255) ,url text , foreign key (playlistId) references songs(songId));");
                   do {
                       songs.displaySongDetails();
                       System.out.print("1 --> Add song \n2 --> Show Menu\nEnter your choice --> ");
                       choice2 = sc.nextInt();
                       if (choice2 == 1)
                       {
                           System.out.print("Enter a song ID from catalog to add it to the playlist --> ");
                           id = sc.nextInt();
                           if(songs.allSongs().contains(id))
                           {
                               song = st.executeUpdate("insert into " + name + "(playlistId,songName,artistName,url) select songId,songName,artistName,url from songs where songId = "+id+";");
                               if (song == 1)
                                   System.out.println("Song added successfully !");
                           }
                           else
                               System.out.print("Invalid Song ID\n");
                       }
                       if(choice2 == 2)
                           displayMenu();
//                       if(choice2 != 1 && choice2 != 2)
//                       {
//                           do
//                           {
//                               System.out.print("Invalid choice. Choose again ! \n");
//                               System.out.print("1 --> Add song \n2 --> Show Menu\nEnter your choice --> ");
//                               choice3 = sc.nextInt();
//                               if(choice3 == 2)
//                               {
//                                   displayMenu();
//                               }
//                           }while (choice3 != 1 && choice3 != 2);
//                       }
                   }while(choice2 != 1 || choice2 != 2);
//                System.out.println("Playlist created successfully !");
        }
        catch (SQLException e)
        {
            System.out.println("Error....Following could be the reasons-:\n 1) Playlist already exists. \n 2) Playlist name has spaces so avoid using spaces.");
            sc.nextLine();
            createPlaylist();
        }
    }
    public static void choosePlaylist()
    {
        try
        {
            ps = connectToDatabase().prepareStatement("SELECT ORDINAL_POSITION,TABLE_NAME FROM information_schema.columns WHERE column_name = \"playlistId\";");
            rs = ps.executeQuery();
           if(!rs.next())
            {
                System.out.println("No playlists to show.");
                System.out.print("1 --> Create a playlist\n2 --> Show Menu\nEnter your choice --> ");
                choice1 = sc.nextInt();
                sc.nextLine();
                if(choice1 == 1)
                    createPlaylist();
                else if(choice1 == 2)
                    displayMenu();
                else
                {
                    do
                    {
                        System.out.println("Invalid choice");
                        System.out.print("1 --> Create a playlist\n2 --> Show Menu\nEnter your choice --> ");
                        choice2 = sc.nextInt();
                        if(choice2 == 2)
                            displayMenu();
                        if(choice2 == 1)
                            createPlaylist();
                    }while (choice2 != 1 && choice2 != 2);
                }
            }
           else
           {
               System.out.println("--------------------------\n|\tYour Playlists\n--------------------------");
               System.out.printf("%-1s %-1s","|Sr No.","\t|Name\n--------------------------\n");
               System.out.printf("|" + count +"\t\t\t|"+ rs.getString(2)+"\n");
               count = 1;
               while (rs.next())
               {
                   count++;
                   System.out.printf("|" + count +"\t\t\t|"+ rs.getString(2)+"\n");
               }
               System.out.println("--------------------------");
           }
        }
        catch (SQLException e)
        {
            System.out.println("Database Error !!!");
        }
    }
    public static void choosePlaylistToDelete()
    {
        try
        {
            ps = connectToDatabase().prepareStatement("SELECT ORDINAL_POSITION,TABLE_NAME FROM information_schema.columns WHERE column_name = \"playlistId\";");
            rs = ps.executeQuery();
            if(!rs.next())
            {
                System.out.println("No playlists to show.");
                do
                {
                    System.out.print("1 --> Menu\nEnter your choice --> ");
                    choice1 = sc.nextInt();
                    if(choice1 == 1)
                        displayMenu();
                }while (choice1 != 1);
            }
            else
            {
                System.out.println("-----------------------\n|\tYour Playlists\n-----------------------");
                System.out.printf("%5s %5s","|Sr No.","\t|Name\n-----------------------\n");
                System.out.printf("|" + count +"\t\t\t|"+ rs.getString(2)+"\n");
                count = 1;
                while (rs.next())
                {
                    count++;
                    System.out.printf("|" +  count + "\t\t\t|" + rs.getString(2)+"\n");
                }
                System.out.println("-----------------------");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Database Error !!!");
        }
    }

    public static void songByAGA(int id)
    {
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select url from songs where songId = "+id+";");
            while (rs.next()) {
                songUrl = new File(rs.getString(1));
                song = AudioSystem.getAudioInputStream(songUrl);
                Clip clip = AudioSystem.getClip();
                clip.open(song);
                clip.start();
                System.out.println("Playing !!");
                while (!userChoice1.equals("X")) {
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------\n" +
                            "|   P --> Play/Resume || Q --> Pause || R --> Replay || S --> Stop || N --> Next || L --> Loop ||  B--> Back || X --> Close   |\n" +
                            "------------------------------------------------------------------------------------------------------------------------------");
                    System.out.print("Enter your choice --> ");
                    userChoice1 = sc.next();
                    userChoice1 = userChoice1.toUpperCase();
                    switch (userChoice1) {
                        case "P":
                            clip.start();
                            System.out.println("Playing...");
                            break;
                        case "Q":
                            clip.stop();
                            System.out.println("Paused... ");
                            break;
                        case "R":
                            clip.setMicrosecondPosition(0);
                            System.out.println("Replaying Current song.");
                            break;
                        case "S":
                            clip.setMicrosecondPosition(0);
                            clip.stop();
                            System.out.println("Stopped !!! ");
                            break;
                        case "N":
                            clip.stop();
                            return;
                        case "L":
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                            System.out.println("Current song is on Repeat !");
                            break;
                        case "B":
                            clip.close();
                            return;
                        case "X":
                            clip.close();
                            System.out.println("\n                                ***** THANKS FOR VIBING *****");
                            System.exit(1);
                        default:
                            System.out.println("Invalid Operation");
                            break;
                    }
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Database Error !!!");
        }
        catch (UnsupportedAudioFileException e)
        {
            System.out.println("Unsupported format of file. Jukebox plays WAV files only.");
        }
        catch (IOException e)
        {
            System.out.println("Error in file !!!");
        }
        catch (LineUnavailableException e)
        {
            System.out.println("Error in file !!!");
        }
    }

    public static void playInOrderFromPlaylist(String name)
    {
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select url,songName,artistName from "+name+";");
            while (rs.next())
            {
                songUrl = new File(rs.getString(1));
                String userChoice1 = "";
                song = AudioSystem.getAudioInputStream(songUrl);
                Clip clip = AudioSystem.getClip();
                clip.open(song);
                clip.start();
                name1 = rs.getString(2);
                name2 = rs.getString(3);
                System.out.println("Playing '"+name1+"' by '"+name2+"'....");
                while (!userChoice1.equals("N"))
                {
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------\n" +
                                       "|   P --> Play/Resume || Q --> Pause || R --> Replay || S --> Stop || N --> Next || L --> Loop || B--> Menu || X --> Close   |\n" +
                                       "------------------------------------------------------------------------------------------------------------------------------");
                    System.out.print("Enter your choice --> ");
                    userChoice1 = sc.next();
                    userChoice1 = userChoice1.toUpperCase();
                    if(userChoice1.equals("N"))
                        System.out.println("Next Song Selected...");
                    switch (userChoice1)
                    {
                        case "P" :
                            clip.start();
                            System.out.println("Playing...");
                            break;
                        case "Q" :
                            clip.stop();
                            System.out.println("Paused... ");
                            break;
                        case "R" :
                            clip.setMicrosecondPosition(0);
                            System.out.println("Replaying Current song.");
                            break;
                        case "S" :
                            clip.setMicrosecondPosition(0);
                            clip.stop();
                            System.out.println("Stopped !!! ");
                            break;
                        case "N" :
                            clip.close();
                            break;
                        case "L" :
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                            System.out.println("Current song is on Repeat !");
                            break;
                        case "B" :
                            clip.close();
                            displayMenu();
                            break;
                        case "X" :
                            clip.close();
                            System.out.println("\n                                ***** THANKS FOR VIBING *****");
                            System.exit(1);
                        default:System.out.println("Invalid Operation");
                            break;
                    }
                }
            }
            if(!rs.next())
                inOrder.playInOrderFromPlaylist(name);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        catch (UnsupportedAudioFileException e)
        {
            System.out.println("Unsupported format of file. Jukebox plays WAV files only.");
        }
        catch (IOException e)
        {
            System.out.println("Error in file !!!");
        }
        catch (LineUnavailableException e)
        {
            System.out.println("Error in file !!!");
        }
    }

}
