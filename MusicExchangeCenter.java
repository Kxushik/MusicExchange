import javafx.util.Pair;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.*;

public class MusicExchangeCenter {
    private ArrayList<User> users;
    private HashMap royalties;
    private HashMap songPop;
    private ArrayList<Song> downloadedSongs;

    public MusicExchangeCenter() {
        users = new ArrayList();
        songPop = new HashMap();
        royalties = new HashMap();
        downloadedSongs = new ArrayList();

    }

    public ArrayList onlineUsers(){
        ArrayList<User> onlineUsers = new ArrayList();
        for (User u : users){
            if (u!=null) {
                if (u.isOnline()) {
                    onlineUsers.add(u);
                }
            }
        }
        return onlineUsers;
    }

    public ArrayList allAvailableSongs(){
        ArrayList<User> onlineUsers = onlineUsers();
        ArrayList<Song> availableSongs = new ArrayList<>();
        for (User curronlineUser: onlineUsers){
            if (curronlineUser!=null) {
                availableSongs.addAll(curronlineUser.getSongList());
            }
        }
        return availableSongs;
    }

    public User userWithName(String s){
        for (User currUser: users){
            if (currUser!=null) {
                if (currUser.getUserName().contains(s)) {
                    return currUser;
                }
            }
        }
        return null;
    }

    public void registerUser(User x){
        if (userWithName(x.getUserName())==null){
            users.add(x);
        }
    }

    public ArrayList availableSongsByArtist(String artist){
        ArrayList<Song> artistSongList = new ArrayList();
        ArrayList<Song> availableSongs = allAvailableSongs();

        for (Song currSong : availableSongs){
            if (currSong!=null){
                if(currSong.getArtist().contains(artist)){
                    artistSongList.add(currSong);
                }
            }
        }
        return artistSongList;
    }

    public Song getSong(String title, String ownerName){
        ArrayList<User> onlineUsers = onlineUsers();
        for (User u : onlineUsers){
                if(u.getUserName().equals(ownerName)){
                    Song getSong = u.songWithTitle(title);
                    if (getSong !=null){
                        downloadedSongs.add(getSong);
                        songPop.put(getSong.getTitle(),(int)songPop.getOrDefault(getSong.getTitle(),0)+1);
                        royalties.put(getSong.getArtist(),(int)royalties.getOrDefault(getSong.getArtist(),0)+1);
                        return getSong;
                    }
                }
        }
        return null;
    }

    public void displayRoyalties(){
        System.out.println(String.format("%-10s","Amount")+"Artist");
        System.out.println("----------------");
        ArrayList<String> uniqueArtists = new ArrayList<>();
        uniqueArtists.addAll(uniqueArtist());
        for (String s : uniqueArtists){
            if (s!=null) {
                System.out.println(String.format("%-10s", "$" + (0.25*(int)royalties.get(s))) + s);
            }
        }
    }

    public TreeSet uniqueDownloads(){
        TreeSet<Song> uniqueTree = new TreeSet<>();
        for (Song s: downloadedSongs){
            if (s!=null){
                uniqueTree.add(s);
            }
        }
        return uniqueTree;
    }

    public HashSet uniqueArtist(){
        HashSet<String> uniqueArtist = new HashSet<>();
        for (Song s: downloadedSongs){
            if (s!=null){
                uniqueArtist.add(s.getArtist());
            }
        }
        return uniqueArtist;
    }

    public ArrayList<Pair<Integer,Song>> songsByPopularity(){
        ArrayList<Pair<Integer,Song>> pairArrayList = new ArrayList<>();
        ArrayList<Song> uniqueDownloads = new ArrayList<>(uniqueDownloads());

        for (Song s:uniqueDownloads){
            Pair newPair = new Pair<Integer,Song>((Integer)songPop.get(s.getTitle()),s);
            pairArrayList.add(newPair);
        }

        Collections.sort(pairArrayList, new Comparator<Pair<Integer,Song>>() {
            public int compare(Pair<Integer,Song> p1, Pair<Integer,Song> p2) {
                int subtraction = p1.getKey()-p2.getKey();
                if (subtraction>0) {
                    return -1;
                }
                if (subtraction==0){
                    return 0;
                }
                return 1;
            }
        });
        return pairArrayList;
    }

    public ArrayList<Song> getDownloadedSongs() {return downloadedSongs;}

    public String toString(){
        int onlineNum = onlineUsers().size();
        int songNum = allAvailableSongs().size();
        String returnString = "Music Exchange Center ( "+onlineNum+ " users online, "+ songNum+" songs available)";
        return returnString;
    }

}