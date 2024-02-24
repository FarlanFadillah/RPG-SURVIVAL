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

public class ArrowProjectile extends Block{
	
	public SpriteSheet ss = new SpriteSheet("/assets/Projectiles/Arrow.png");
	ImageManager im = new ImageManager();
	
	float velX = 0;
	float velY = 0;
	float rotate;
	boolean collisioncheck = true;
	
	private GameObject Enemy;
	public String arah;
	
	public ArrowProjectile(int x, int y, ID id, BlockType bt, Game game, int mx, int my, float rotate, String arah) {
		super(x, y, id, bt, game);
		velX = (mx - x) / 15;
		velY = (my - y) / 15;
		this.rotate = rotate;
		image = ss.grabImage(1, 1, 64, 64);
		start = game.second;
		this.arah = arah;
		
	}

	public void tick() {
		x += velX;
		y += velY;
		attackCollision();
		stop = game.second;
		if(stop - start >= 5) {
			game.tryWorld.objectLayer.get(0).remove(this);
		}
		
	}

	public void render(Graphics g) {
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(rotate), locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		//g.fillRect(getBound().x, getBound().y, getBound().width, getBound().height);
		g.drawImage(op.filter(image, null), x-32, y-32, null);
		
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
			for (int i = 0; i < game.tryWorld.objectLayer.get(0).size(); i++) {
				GameObject temp = game.tryWorld.objectLayer.get(0).get(i);
				if(this != temp && getBound().intersects(temp.getBound()) && temp.getID() == ID.Entity) {
						Entity temp2 = (Entity)temp;
						if(temp2.getEntityType() == EntityType.Monster) {
							image = ss.grabImage(1, 2, 64, 64);
							velX = 0;
							velY = 0;
							//temp2.hp -= 10;
							collisioncheck = false;
							Enemy = temp2;
					}
				}
			}
		}else {
			switch (arah) {
			case "atas":
				x = Enemy.x+(int)(Enemy.getSize().getWidth()/2);
				y = Enemy.y+(int)(Enemy.getSize().getHeight()/2)+25;
								
				break;

			case "ataskanan":
				x = Enemy.x+(int)(Enemy.getSize().getWidth()/2)-20;
				y = Enemy.y+(int)(Enemy.getSize().getHeight()/2)+20;
				
				break;
			
			case "ataskiri":
				x = Enemy.x+(int)(Enemy.getSize().getWidth()/2)+20;
				y = Enemy.y+(int)(Enemy.getSize().getHeight()/2)+20;
				
				break;
			
			case "kanan":
				x = Enemy.x+(int)(Enemy.getSize().getWidth()/2)-25;
				y = Enemy.y+(int)(Enemy.getSize().getHeight()/2);
				
				break;
			
			case "kiri":
				x = Enemy.x+(int)(Enemy.getSize().getWidth()/2)+25;
				y = Enemy.y+(int)(Enemy.getSize().getHeight()/2);
				
				break;
			
			case "bawahkanan":
				x = Enemy.x+(int)(Enemy.getSize().getWidth()/2)-20;
				y = Enemy.y+(int)(Enemy.getSize().getHeight()/2)-20;
				
				break;
			
			case "bawahkiri":
				x = Enemy.x+(int)(Enemy.getSize().getWidth()/2)+20;
				y = Enemy.y+(int)(Enemy.getSize().getHeight()/2)-20;
				
				break;
				
			case "bawah":
				x = Enemy.x+(int)(Enemy.getSize().getWidth()/2);
				y = Enemy.y+(int)(Enemy.getSize().getHeight()/2)-25;
				
				break;
				
			}
		
		}
		
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
