import com.dao.DaoImpl;
import com.view.AllAlbums;
import com.view.AllArtists;
import com.view.AllGenres;
import com.view.AllSongs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.util.List;

public class JukeboxDemo
{
    AllArtists artist;
    AllGenres genre;
    AllAlbums album;
    AllSongs  song;
    @Before
    public void setUp()
    {
        artist = new AllArtists();
        genre= new AllGenres();
        album = new AllAlbums();
        song = new AllSongs();
    }
    @After
    public void teardown()
    {
        artist = null;
        genre = null;
        album = null;
        song = null;
    }
    @Test
    public void returnSongById()
    {
        List songs = song.songById(2);
        String song = (String) songs.get(0);
        song =song.toLowerCase();
        assertEquals("zones",song);
    }
    @Test
    public void returnSongByArtist()
    {
        List songs = artist.songByArtist("MAKO");
        String song = (String) songs.get(0);
        song =song.toLowerCase();
        assertEquals("as we fall",song);
    }
    @Test
    public void returnSongByGenre()
    {
        List songs = genre.songByGenre("METAL");
        String song = (String) songs.get(1);
        song =song.toLowerCase();
        assertEquals("blacksmith",song);
    }
    @Test
    public void returnSongByAlbum()
    {
        List songs = album.songByAlbum("FROZEN");
        String song = (String) songs.get(0);
        song =song.toLowerCase();
        assertEquals("frozen",song);
    }

}
