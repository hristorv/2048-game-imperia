package com.imperia.finalproject.model;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

/**
 * Implements the singleton pattern.Use getAnimations() method to get an
 * instance of the animations.
 * 
 */
public class Animations {
	private static final int DEFAUT_TIME_FOR_ANIMATION = 150;
	private static final float FIFTY_PERCENT_OF_DEFAULT_SCALE = 0.5f;
	private static final float COLLISION_SCALE = 1.2f;
	private static final int NOT_SCALED = 0;
	private static final int DEFAULT_SCALE = 1;
	private static Animations animations;
	public AnimationSet collisionAnimation;
	public AnimationSet generationAnimation;

	private Animations() {
		collisionAnimation = getCollisionAnimation();
		generationAnimation = getGenerationAnimation();
	}

	public static Animations getAnimations() {
		if (animations == null)
			animations = new Animations();
		return animations;
	}

	/**
	 * Initializes a scale animation,set its duration to 150 milliseconds.The
	 * scale animation starts from the center as the pivot point is at 50%
	 * relative to self.
	 * 
	 * @return The initialized animation.
	 */
	private AnimationSet getCollisionAnimation() {
		ScaleAnimation animScale = new ScaleAnimation(DEFAULT_SCALE,
				COLLISION_SCALE, DEFAULT_SCALE, COLLISION_SCALE,
				Animation.RELATIVE_TO_SELF, FIFTY_PERCENT_OF_DEFAULT_SCALE,
				Animation.RELATIVE_TO_SELF, FIFTY_PERCENT_OF_DEFAULT_SCALE);
		animScale.setDuration(DEFAUT_TIME_FOR_ANIMATION);
		AnimationSet animation = new AnimationSet(false);
		animation.addAnimation(animScale);
		return animation;
	}

	/**
	 * Initializes a scale animation,set its duration to 150 milliseconds.The
	 * scale animation starts from the center as the pivot point is at 50%
	 * relative to self.
	 * 
	 * @return The initialized animation.
	 */
	private AnimationSet getGenerationAnimation() {
		ScaleAnimation animScale = new ScaleAnimation(NOT_SCALED,
				DEFAULT_SCALE, NOT_SCALED, DEFAULT_SCALE,
				Animation.RELATIVE_TO_SELF, FIFTY_PERCENT_OF_DEFAULT_SCALE,
				Animation.RELATIVE_TO_SELF, FIFTY_PERCENT_OF_DEFAULT_SCALE);
		animScale.setDuration(DEFAUT_TIME_FOR_ANIMATION);
		AnimationSet animation = new AnimationSet(false);
		animation.addAnimation(animScale);
		return animation;
	}
}
