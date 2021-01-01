/*    */ package fi.johvu.motimusic;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.SoundCategory;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */

public class Song {
    private final MotiMusic main;

    private int currentTick;

    private int tickTime;

    private String song;

    private Player player;

    private ArrayList<String> notes;

    private int noteIndex;

    private boolean isEnded;

    public Song(int tickTime, String song, Player player, MotiMusic main) {
        this.currentTick = 0;
        this.tickTime = tickTime;
        this.song = song;
        this.player = player;
        this.main = main;
        this.notes = new ArrayList<>();
        this.noteIndex = 0;
        this.notes.addAll(Arrays.asList(this.song.split(" ")));
    }

    public void tick() {
        if (this.noteIndex == this.notes.size()) {
            setEnded(true);
            return;
        }
        this.currentTick++;
        if (this.currentTick == this.tickTime) {
            if (this.main.notes.get(this.notes.get(this.noteIndex)) == null) {
                this.noteIndex++;
                return;
            }
            this.player.playSound(this.player.getLocation(), "violin", SoundCategory.MASTER, 100.0F, ((Float)this.main.notes.get(this.notes.get(this.noteIndex))).floatValue());
            this.noteIndex++;
            return;
        }
        if (this.currentTick >= this.tickTime)
            this.currentTick = 0;
    }

    public boolean isEnded() {
        return this.isEnded;
    }

    public void setEnded(boolean ended) {
        this.isEnded = ended;
    }
}
