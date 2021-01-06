public class Song implements Comparable<Song> {
  private String title;
  private String artist;
  private int duration;
  private User owner;

  public Song() {
    this("", "", 0, 0);
  }

  public Song(String t, String a, int m, int s) {
    title = t;
    artist = a;
    duration = m * 60 + s;
    owner = null;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public User getOwner() {
    return owner;
  }

  public String getTitle() {
    return title;
  }

  public String getArtist() {
    return artist;
  }

  public int getDuration() {
    return duration;
  }

  public int getMinutes() {
    return duration / 60;
  }

  public int getSeconds() {
    return duration % 60;
  }


  public int compareTo(Song s){
    return this.title.compareTo(s.title);
  }

  public String toString() {
    return ("\"" + title + "\" by " + artist + " " + (duration / 60) + ":" + (duration % 60));
  }
}
