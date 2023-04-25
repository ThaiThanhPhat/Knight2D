package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CollisionCheck;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 10;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down;
	private float playerSpeed = 3.0f;
	private int[][] lvlData;
	private float xDrawOffset = 45 * Game.SCALE;
	private float yDrawOffset = 50 * Game.SCALE;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 22 * Game.SCALE, 30 * Game.SCALE);
	}

	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
		drawHitbox(g);
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}

		}

	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;

		if (attacking)
			playerAction = ATTACK;

		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;

		if (!left && !right && !up && !down)
			return;

		float xSpeed = 0, ySpeed = 0;

		if (left && !right)
			xSpeed = -playerSpeed;
		else if (right && !left)
			xSpeed = playerSpeed;

		if (up && !down)
			ySpeed = -playerSpeed;
		else if (down && !up)
			ySpeed = playerSpeed;
			if (CollisionCheck(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.x += xSpeed;
				hitbox.y += ySpeed;
				moving = true;
			}
	}

	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

		animations = new BufferedImage[8][10];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 120, j * 82, 120, 82);

	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean getLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean getUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean getRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean getDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

}