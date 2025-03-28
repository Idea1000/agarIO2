package fr.unicaen.iutcaen.config;

public class Config {
    /**
    * These constants can be modified to change the game behavior
    * */

    //Speed of all the moving cells---------\\
    public final static double BASE_SPEED = 25;
    public final static double MIN_SPEED = 1;
    public final static double SPEED_COEF = 25; // the lower, the faster you are

    //--------------------------------------\\




    //Coefficient that calculates the size of different things --\\
    public final static double SIZE_RATIO = 2.5;
    public final static double SIZE_RATIO_PELLET = 8;
    public final static double SIZE_RATIO_CELL = 10;

    //----------------------------------------------------------\\




    //Number of layers the quad tree possess--------------------\\
    public final static int DEPTH_MAX_QT_TREE = 6;

    //----------------------------------------------------------\\




    //Size of the map in pixels---------------------------------\\
    public final static double MAP_HEIGHT = 40000;
    public final static double MAP_WIDTH = 40000;

    //----------------------------------------------------------\\





    //Some pellets values---------------------------------------\\
    public final static int PELLETS_NUM = 2000;  // the total number of pellets in the map
    public final static double BOUND_MASS_PELLET = 50;
    public final static double MIN_MASS_PELLET = 10;

    //----------------------------------------------------------\\



    public final static int UPDATE = 33; //The number of millisecond we wait before refreshing (33 means 30FPS)




    //Size of the screen----------------------------------------\\
    public static final int SCREEN_WIDTH = 1500;
    public static final int SCREEN_HEIGHT = 900;


    //----------------------------------------------------------\\




    //Game Rules------------------------------------------------\\
    public static final double SIZE_RATIO_TO_EAT = 1.33; // The coef to see if you're big enough to eat another Cell (1.33 by default)

    public static final double MAX_LENGTH_SPEED = 200.0; // The distance where moving your mouse further won't increase your speed
    public static final double MASS_REQUIRED_TO_SPLIT = 150.0;
    public static final double BURST_SPEED = 10;
    public static final double BASE_SPLIT_TIME = 150; // the minimal time of splitting
    public static final double SPLIT_TIME_COEF_MASS = 3.0; //the mass coefficient to increase split timer. The bigger the more it takes time

    //----------------------------------------------------------\\



    public final static int MAX_LOCAL_AI_NUMBER = 30;

}
