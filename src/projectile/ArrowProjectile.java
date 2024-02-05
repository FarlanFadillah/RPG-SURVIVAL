package projectile;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.filehandler.SpriteSheet;
import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;
import com.tile.ImageManager;

public class ArrowProjectile extends Block{
	
	public SpriteSheet ss = new SpriteSheet("/assets/Projectiles/Arrow.png");
	ImageManager im = new ImageManager();
	
	int velX = 0;
	int velY = 0;

	public ArrowProjectile(int x, int y, ID id, BlockType bt, Game game, int mx, int my) {
		super(x, y, id, bt, game);
		velX = (mx - x) / 15;
		velY = (my - y) / 15;
		
		image = ss.grabImage(1, 1, 64, 64);
	}

	public void tick() {
		x += velX;
		y += velY;
		
	}

	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
		
	}

	public Rectangle renderOrder() {
		return new Rectangle();
	}

	public void getImage() {
		
	}

	public Rectangle getBound() {
		return new Rectangle(x, y, 64, 64);
	}

	@Override
	public Rectangle getSize() {
		// TODO Auto-generated method stub
		return new Rectangle(x, y, image.getWidth(), image.getHeight());
	}

	@Override
	public void hit() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'hit'");
	}

}
