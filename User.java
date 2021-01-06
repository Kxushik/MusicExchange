import java.util.ArrayList;

public class User {
  private String     userName;
  private boolean    online;
  private ArrayList<Song> songList;
  
  public User()  {this("");}
  
  public User(String u)  {
    userName = u;
    online = false;
    songList = new ArrayList<>();
  }

  public String getUserName() { return userName; }
  public boolean isOnline() { return online; }
  public ArrayList<Song> getSongList() {return songList;}

  public void addSong (Song s){
    Song newSong = new Song(s.getTitle(),s.getArtist(),s.getMinutes(),s.getSeconds());
    songList.add(newSong);
    newSong.setOwner(this);
  }

  public int totalSongTime(){
    int songListTime=0;
    for (Song m : songList) {
      if (m != null) {
        songListTime += m.getDuration();
      }
    }
    return songListTime;
  }

  public ArrayList<String> requestCompleteSonglist(MusicExchangeCenter m){
    ArrayList<Song> allAvailableSongs = m.allAvailableSongs();
    ArrayList<String> allAvailableSongsString = new ArrayList<>();
    int songCount=1;
    allAvailableSongsString.add(String.format("%-30s","Title") + String.format("%-20s","Artist") + String.format("%-5s","TIME") + String.format("%-10s","OWNER"));
    for (Song s : allAvailableSongs){
      if (s!=null){
        String Title = String.format("%-30s",songCount+". "+s.getTitle());
        String Artist = String.format("%-20s",s.getArtist());

        String timeMinute = ""+s.getMinutes();
        int timeSecondsINT = s.getSeconds();
        String timeSeconds;
        if (Integer.toString(timeSecondsINT).length()==1){timeSeconds = String.format("%02d",timeSecondsINT);}
        else{timeSeconds = String.format("%02d",timeSecondsINT);}
        timeSeconds = String.format("%-5s",timeSeconds);

        String Owner = String.format("%-10s",s.getOwner().getUserName());

        allAvailableSongsString.add(Title+Artist+timeMinute+":"+timeSeconds+Owner);
        songCount++;
      }
    }
    return allAvailableSongsString;
  }

  public ArrayList<String> requestSonglistByArtist(MusicExchangeCenter m, String artist){
    ArrayList<Song> availableArtistList = m.availableSongsByArtist(artist);
    ArrayList<String> availableArtistString = new ArrayList<>();
    int songCount=1;
    availableArtistString.add(String.format("%-30s","Title") + String.format("%-20s","Artist") + String.format("%-5s","TIME") + String.format("%-10s","OWNER"));
    for (Song s : availableArtistList){
      if (s!=null){
        String Title = String.format("%-30s",songCount+". "+s.getTitle());
        String Artist = String.format("%-20s",s.getArtist());

        String timeMinute = ""+s.getMinutes();
        int timeSecondsINT = s.getSeconds();
        String timeSeconds;
        if (Integer.toString(timeSecondsINT).length()==1){timeSeconds = String.format("%02d",timeSecondsINT);}
        else{timeSeconds = String.format("%02d",timeSecondsINT);}
        timeSeconds = String.format("%-5s",timeSeconds);

        String Owner = String.format("%-10s",s.getOwner().getUserName());

        availableArtistString.add(Title+Artist+timeMinute+":"+timeSeconds+Owner);
        songCount++;
      }
    }
    return availableArtistString;

  }

  public void register(MusicExchangeCenter m ){
    m.registerUser(this);
  }

  public void logon(){
    online=true;
  }

  public void logoff(){
    online=false;
  }

  public Song songWithTitle(String title){
    for (Song s : songList){
      if (s!=null){
        if (s.getTitle().contains(title)){
          return s;
        }
      }
    }
    return null;
  }

  public void downloadSong(MusicExchangeCenter m, String title, String ownerName){
    Song addSongObj = m.getSong(title, ownerName);
    if (addSongObj!=null){
      addSong(addSongObj);
    }
  }

  public String toString()  {
    String s = "" + userName + ": "+ songList.size() +" songs (";
    if (!online) s += "not ";
    return s + "online)";
  }
}
