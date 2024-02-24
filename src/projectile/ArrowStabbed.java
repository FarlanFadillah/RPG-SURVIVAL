package projectile;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import com.filehandler.SpriteSheet;
import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;
import com.tile.ImageManager;

public class ArrowStabbed extends Block{
	
	public SpriteSheet ss = new SpriteSheet("/assets/Projectiles/Arrow.png");
	ImageManager im = new ImageManager();

	int velX = 0;
	int velY = 0;
	float rotate;
	public ArrowStabbed(int x, int y, ID id, BlockType bt, Game game, int mx, int my, float rotate) {
		super(x, y, id, bt, game);
		velX = (mx - x) / 15;
		velY = (my - y) / 15;
		this.rotate = rotate;
		image = ss.grabImage(1, 2, 64, 64);
	}

	public void tick() {
		x += velX;
		y += velY;
		
	}

	public void render(Graphics g) {
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(rotate), locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		g.drawImage(op.filter(image, null), x-32, y-32, null);
		
	}

	public Rectangle renderOrder() {
		return new Rectangle();
	}

	public void getImage() {
		
	}

	public Rectangle getBound() {
		return new Rectangle(x, y, 58, 6);
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
