package frc.robot.commands.auton.routines;

import com.ctre.phoenix.music.Orchestra;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PlayMusic extends CommandBase {

    final double NOTE_OFF = (double) 0;
    final double NOTE_E6 = (double) 1318.51;
    final double NOTE_B5 = (double) 987.767;
    final double NOTE_A5sharp = (double) 932.328;
    final double NOTE_A5 = (double) 880;
    final double NOTE_G5sharp = (double) 830.609;
    final double NOTE_G5 = (double) 783.991;
    final double NOTE_F5sharp = (double) 739.989;
    final double NOTE_F5 = (double) 698.456;
    final double NOTE_E5 = (double) 659.255;
    final double NOTE_D5sharp = (double) 622.254;
    final double NOTE_DS5 = (double) 622.254;
    final double NOTE_D5 = (double) 587.33;
    final double NOTE_C5sharp = (double) 554.365;
    final double NOTE_C5 = (double) 523.251;
    final double NOTE_B4 = (double) 493.883;
    final double NOTE_A4sharp = (double) 466.164;
    final double NOTE_A4 = (double) 440;
    final double NOTE_G4sharp = (double) 415.305;
    final double NOTE_GS4 = (double) 415.305;
    final double NOTE_G4 = (double) 391.995;
    final double NOTE_F4sharp = (double) 369.994;
    final double NOTE_F4 = (double) 349.228;
    final double NOTE_E4 = (double) 329.628;
    final double NOTE_D4sharp = (double) 311.127;
    final double NOTE_DS4 = (double) 311.127;
    final double NOTE_D4 = (double) 293.665;
    final double NOTE_C4sharp = (double) 277.183;
    final double NOTE_C4 = (double) 261.626;

    private double furElis[] = { NOTE_E5, NOTE_DS5, NOTE_E5, NOTE_DS5, NOTE_E5, NOTE_B4, NOTE_D5, NOTE_C5, NOTE_A4, NOTE_C4,
            NOTE_E4, NOTE_A4, NOTE_B4, NOTE_E4, NOTE_GS4, NOTE_B4, NOTE_C5, NOTE_E4, NOTE_E5, NOTE_DS5, NOTE_E5, NOTE_DS5,
            NOTE_E5, NOTE_B4, NOTE_D5, NOTE_C5, NOTE_A4, NOTE_C4, NOTE_E4, NOTE_A4, NOTE_B4, NOTE_E4, NOTE_C5, NOTE_B4,
            NOTE_A4, NOTE_B4, NOTE_C5, NOTE_D5, NOTE_E5, NOTE_G4, NOTE_F5, NOTE_E5, NOTE_D5, NOTE_F4, NOTE_E5, NOTE_D5,
            NOTE_C5, NOTE_E4, NOTE_D5, NOTE_C5, NOTE_B4, NOTE_E4, NOTE_E5, NOTE_E4, NOTE_E5, NOTE_E4, NOTE_E5, NOTE_E4,
            NOTE_E5, NOTE_DS4, NOTE_E5, NOTE_D4, NOTE_E5, NOTE_DS4, NOTE_E5, NOTE_B4, NOTE_D5, NOTE_C5, NOTE_A4, NOTE_C4,
            NOTE_E4, NOTE_A4, NOTE_B4, NOTE_E4, NOTE_GS4, NOTE_B4, NOTE_C5, NOTE_E4, NOTE_E5, NOTE_DS5, NOTE_E5, NOTE_DS5,
            NOTE_E5, NOTE_B4, NOTE_D5, NOTE_C5, NOTE_A4, NOTE_C4, NOTE_E4, NOTE_A4, NOTE_B4, NOTE_E4, NOTE_C5, NOTE_B4,
            NOTE_A4, NOTE_OFF, };

    private int furElisDurationsMs[] = { 111, 111, 111, 111, 111, 111, 111, 111, 333, 111, 111, 111, 333, 111, 111, 111, 333, 111, 111,
            111, 111, 111, 111, 111, 111, 111, 333, 111, 111, 111, 333, 111, 111, 111, 333, 111, 111, 111, 333, 111, 111, 111,
            333, 111, 111, 111, 333, 111, 111, 111, 111, 111, 111, 111, 111, 111, 111, 111, 111, 111, 111, 111, 111, 111, 111,
            111, 111, 111, 333, 111, 111, 111, 333, 111, 111, 111, 333, 111, 111, 111, 111, 111, 111, 111, 111, 111, 333, 111,
            111, 111, 333, 125, 125, 125, 999, 111, };
    private int songSelection;
    private int timeToPlayLoops;
    private Orchestra orchestra;

    public PlayMusic(String[] musicFiles) {

        orchestra = new Orchestra();

    }

    @Override
    public void initialize() {
        loadMusicSelection(0);
    }

    private void loadMusicSelection(int offset)
    {
//        /* increment song selection */
//        _songSelection += offset;
//        /* wrap song index in case it exceeds boundary */
//        if (_songSelection >= _songs.length) {
//            _songSelection = 0;
//        }
//        if (_songSelection < 0) {
//            _songSelection = _songs.length - 1;
//        }
//        /* load the chirp file */
//        orchestra.loadMusic(_songs[_songSelection]);
//
//        /* print to console */
//        System.out.println("Song selected is: " + _songs[_songSelection] + ".  Press left/right on d-pad to change.");
//
//        /* schedule a play request, after a delay.
//            This gives the Orchestra service time to parse chirp file.
//            If play() is called immedietely after, you may get an invalid action error code. */
//        _timeToPlayLoops = 10;
    }
}
