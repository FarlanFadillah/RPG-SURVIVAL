package projectile;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import com.filehandler.SpriteSheet;
import com.id.BlockType;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;
import com.obj.Entity;
import com.obj.GameObject;
import com.tile.ImageManager;

public class TNTProjectile extends Block{
	
	public SpriteSheet ss = new SpriteSheet("/assets/Projectiles/Dynamite.png");
	public SpriteSheet ss2 = new SpriteSheet("/assets/Effects/Explosions.png");
	ImageManager im = new ImageManager();
	
	public float GRAVITYy = 0;
	
	float velX = 0;
	float velY = 0;
	float rotate;
	boolean collisioncheck = true;
	
	public String arah;
	
	public TNTProjectile(int x, int y, ID id, BlockType bt, Game game, int mx, int my, float rotate, String arah) {
		super(x, y, id, bt, game);
		int diffX = mx - x;
        int diffY = my - y;
        
        //if(arah == "kiri" || arah == "kanan") {
        //	GRAVITYy = 0.5f;
        //}

        // Hitung sudut antara kedua titik
        double angle = Math.atan2(diffY, diffX);

        // Hitung komponen kecepatan x dan y berdasarkan sudut
        velX = (float) (Math.cos(angle) * 5);
        velY = (float) (Math.sin(angle) * 5);
		this.rotate = rotate;
		image = ss.grabImage(1, 1, 64, 64);
		start = game.second;
		this.arah = arah;
		
	}

	public void tick() {
		//velY += GRAVITYy;
		
		x += velX;
		y += velY;
		rotate += 25;
		attackCollision();
		stop = game.second;
		if(stop - start >= 5) {
			game.tryWorld.entity.remove(this);
		}
		
	}

	public void render(Graphics g) {
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(rotate), locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		//g.fillRect(getBound().x, getBound().y, getBound().width, getBound().height);
		g.drawImage(op.filter(image, null), x, y, null);
		
		
	}

	public Rectangle renderOrder() {
		return new Rectangle();
	}

	public void getImage() {
		
	}

	public Rectangle getBound() {
		return new Rectangle(x, y, 4, 4);
	}
	
	public void attackCollision() {
		
		if(collisioncheck == true) {
			for (int i = 0; i < game.tryWorld.objects.size(); i++) {
				GameObject temp = game.tryWorld.objects.get(i);
				if(this != temp && getBound().intersects(temp.getBound()) && temp.getID() == ID.Entity && temp.getClass() != TNTProjectile.class) {
						Entity temp2 = (Entity)temp;
						if(temp2.getEntityType() == EntityType.Player) {
							image = ss2.grabImage(1, 1, 64, 64);
							temp2.hp -= 50;
							collisioncheck = false;
					}
				}
			}
		}
		
	}

	@Override
	public Rectangle getSize() {
		// TODO Auto-generated method stub
		return new Rectangle(x, y, image.getWidth(), image.getHeight());
	}

	@Override
    public void hit(int damage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }

}
