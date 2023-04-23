package com.dao;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SongOperations extends DaoImpl
{
    public static void playSong(int id)
    {
        ResultSet names = null;
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select url,songName,artistName from songs where songId = "+id+";");
            while (rs.next())
            {
                songUrl = new File(rs.getString(1));
                song = AudioSystem.getAudioInputStream(songUrl);
                Clip clip = AudioSystem.getClip();
                clip.open(song);
                clip.start();
                name1 = rs.getString(2);
                name2 = rs.getString(3);
                System.out.println("Playing '"+name1+"' by '"+name2+"'....");
                while (!userChoice1.equals("X"))
                {
                    System.out.println("-----------------------------------------------------------------------------------------------------------------\n" +
                            "|   P --> Play/Resume || Q --> Pause || R --> Replay || S --> Stop || L --> Loop ||  B--> Back || X --> Close   |\n" +
                            "-----------------------------------------------------------------------------------------------------------------");
                    System.out.print("Enter your choice --> ");
                    userChoice1 = sc.next();
                    userChoice1 = userChoice1.toUpperCase();

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
                        case "L" :
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                            System.out.println("Current song is on Repeat !");
                            break;
                        case "B" :
                            clip.close();
                            return;
                        case "X" :
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
    public static void playSong(String name)
    {
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select url,songName,artistName from songs where songName = \""+name+"\";");
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
                    System.out.println("P --> Play/Resume || Q --> Pause || R --> Replay || S --> Stop || B--> Back || X --> Close");
                    System.out.print("Enter your choice --> ");
                    userChoice1 = sc.next();
                    userChoice1 = userChoice1.toUpperCase();
                    if(userChoice1.equals("N"))
                        System.out.println("Next Song Selected...");
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
            if(rs.getString(1) == "")
                System.out.println("Song not found !");
        }
        catch (SQLException e)
        {
            System.out.println("Song not found !");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void playInOrder()
    {
        try
        {
            st = connectToDatabase().createStatement();
            rs = st.executeQuery("select url,songName,artistName from songs;");
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
                do
                {
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------\n" +
                            "|   P --> Play/Resume || Q --> Pause || R --> Replay || S --> Stop || N --> Next || L --> Loop || B--> Back || X --> Close   |\n" +
                            "------------------------------------------------------------------------------------------------------------------------------");
                    System.out.print("Enter your choice --> ");
                    userChoice1 = sc.next();
                    userChoice1 = userChoice1.toUpperCase();
                    if(userChoice1.equals("N"))
                        System.out.println("Next song selected...");
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
                        case "R":
                            clip.setMicrosecondPosition(0);
                            System.out.println("Replaying Current song.");
                            break;
                        case "S" :
                            clip.setMicrosecondPosition(0);
                            clip.stop();
                            System.out.println("Stopped !!! ");
                            break;
                        case "N" :
                            clip.stop();
                            break;
                        case "L" :
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                            System.out.println("Current song is on Repeat !");
                            break;
                        case "B" :
                            clip.close();
                            return;
                        case "X" :
                            clip.close();
                            System.out.println("\n                                ***** THANKS FOR VIBING *****");
                            System.exit(1);
                        default:System.out.println("Invalid Operation");
                            break;
                    }
                }while (!userChoice1.equals("N"));
            }
            if(!rs.next())
                playInOrder();
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
