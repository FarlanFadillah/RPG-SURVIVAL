package com.id;

public enum ID {
    Entity(), Block(), Item(), Skill(), Blueprint();
	
	//Game State Section
    public static int PLAY_STATE 		= 1;
    public static int PAUSE_STATE 		= 2;
    public static int MENU_STATE		= 3;
    public static int INVENT_STATE 		= 4;
    public static int SKILLTAB_STATE	= 5;
    public static int MAP_STATE 		= 6;
    public static int BPWINDOW_STATE	= 7;
}
