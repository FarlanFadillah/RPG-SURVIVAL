package com.tile;

public class TileManager {
    public TileMap[] map = new TileMap[300000];


    public void getTiles() {
		System.out.println("Getting Tiles!!");
		map[0] = new TileMap();
		map[0].image = ss.grabImage(43, 44, 32, 32);
		int c = 1;
		int x = 1;
		int y = 1;
		while(x<ss.image.getWidth()/32 && y<ss.image.getHeight()/32 +1 ) {
			
			while(x<=ss.image.getWidth()/32) {
				
				try {
					map[c] = new TileMap();
					map[c].image = ss.grabImage(x, y, 32, 32);
					//System.out.println(x +" "+y+" "+c);
					c++;
					x++;
					if(c==ss.image.getWidth()/32*ss.image.getHeight()/32) {
						break;
					}
				}catch (Exception e) {
					System.out.println(e);
				}
				
			}
			if(x>ss.image.getWidth()/32) {
				x=1;
				y++;
			}
		}
	}
}
