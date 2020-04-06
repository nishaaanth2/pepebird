package com.nyow.peppe;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class PepeBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background,logo;
	Texture[] bird;
	int birdstate=0,pause=0;
	float gravity=0.2f;
	float velocity=0;
	int birdY=0;

	ArrayList<Integer> coinx = new ArrayList<Integer>();
	ArrayList<Integer> coiny = new ArrayList<Integer>();
	Texture coin;
	Random random;
	int coincount;

	ArrayList<Integer> bombx = new ArrayList<Integer>();
	ArrayList<Integer> bomby = new ArrayList<Integer>();
	Texture bomb;
	int bombcount;

	ArrayList<Rectangle> coinRect= new ArrayList<>();
	ArrayList<Rectangle> bombRect= new ArrayList<>();

	Rectangle birdReact;
	int score=0;

	BitmapFont font;

	int gamestate=0;
	Texture dizzy;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		logo = new Texture("logo.png");
		bird= new Texture[4];
		bird[0]= new Texture("bird1.png");
		bird[1]= new Texture("bird2.png");
		coin=new Texture("toptube.png");
		bomb=new Texture("bottomtube.png");
		dizzy=new Texture("gameover.png");
		random= new Random();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
		birdY= Gdx.graphics.getHeight()/2;
	}

	public void makeCoin()
	{
		int height =random.nextInt(3);
		if(height==0){
			height=Gdx.graphics.getHeight()/5;
			coiny.add(height*2);
			coinx.add(Gdx.graphics.getWidth());
			bomby.add((height*1)-bomb.getHeight()-100);
			bombx.add(Gdx.graphics.getWidth());
		}
		else if(height==1){

			height=Gdx.graphics.getHeight()/5;
			coiny.add(height*3);
			coinx.add(Gdx.graphics.getWidth());
			bomby.add((height*2)-bomb.getHeight()-100);
			bombx.add(Gdx.graphics.getWidth());

		}
		else if (height==2){

			height=Gdx.graphics.getHeight()/5;
			coiny.add(height*4);
			coinx.add(Gdx.graphics.getWidth());
			bomby.add((height*3)-bomb.getHeight()-100);
			bombx.add(Gdx.graphics.getWidth());

		}

//		height=(Gdx.graphics.getHeight()*(height/4));
		//coiny.add(height);


//		float height1 =random.nextFloat()*Gdx.graphics.getHeight();

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gamestate==1) ///game is runnin
		{

			// hi
//			if(bombcount <250) {
//			}
//			else {
//				bombcount=0;
//				makeBomb();
//			}
//			bombRect.clear();
//
//			for (int i=0; i<bombx.size();i++){
//				batch.draw(bomb,bombx.get(i),bomby.get(i));
//				bombx.set(i,bombx.get(i)-8);
//				bombRect.add(new Rectangle(bombx.get(i),bomby.get(i),bomb.getWidth(),bomb.getHeight()));
//			}

			if(coincount <200)
			{
				coincount++;
				bombcount++;

			}
			else
			{
				coincount=0;
				bombcount=0;
				makeCoin();
			}

			coinRect.clear();
			bombRect.clear();
			for (int i=0; i<coinx.size();i++){
				batch.draw(coin,coinx.get(i),coiny.get(i));
				coinx.set(i,coinx.get(i)-3);
				coinRect.add(new Rectangle(coinx.get(i),coiny.get(i)+50,coin.getWidth(),coin.getHeight()));

				batch.draw(bomb,bombx.get(i),bomby.get(i));
				bombx.set(i,bombx.get(i)-3);
				bombRect.add(new Rectangle(bombx.get(i),bomby.get(i)+50,bomb.getWidth(),bomb.getHeight()));

				if(coinx.get(i)==(Gdx.graphics.getWidth()/2))
				{
					score++;
				}

			}

			if (Gdx.input.justTouched())
			{
				velocity=-10;
			}
			if(pause<8)
			{
				pause++;
			}
			else
			{
				pause=0;
				if(birdstate<1)
				{
					birdstate++;
				}
				else {
					birdstate=0;
				}
			}
			if(birdY<=0)
			{
				birdY=0;
			}
			if(birdY>= Gdx.graphics.getHeight() - bird[birdstate].getHeight()/2)
			{
				birdY=Gdx.graphics.getHeight()- bird[birdstate].getHeight()/2;
			}
			velocity+=gravity;
			birdY -=velocity;


			//,,,,,,,
		}
		else if (gamestate ==0)// abt to start
		{
				batch.draw(logo,Gdx.graphics.getWidth()/2 - logo.getWidth()/2,Gdx.graphics.getHeight()/2 +250	);

			if (Gdx.input.justTouched())//restart
			{
				gamestate=1;
			}
		}
		else if (gamestate ==2)//gameover
		{
			if (Gdx.input.justTouched())//restart
			{
				gamestate=1;
				birdY= Gdx.graphics.getHeight()/2;
				score=0;
				velocity=0;
				coinx.clear();
				coiny.clear();
				coinRect.clear();
				coincount=0;
				bombx.clear();
				bomby.clear();
				bombRect.clear();
				bombcount=0;
			}

		}


		if(gamestate==2){
			batch.draw(dizzy,Gdx.graphics.getWidth()/2 - dizzy.getWidth()/2,Gdx.graphics.getHeight()/2);
		}
		else
		{
			batch.draw(bird[birdstate],Gdx.graphics.getWidth()/2 - bird[birdstate].getWidth()/2,birdY- bird[birdstate].getHeight()/2);

		}





		birdReact=new Rectangle(Gdx.graphics.getWidth()/2 - bird[birdstate].getWidth()/2 ,birdY , bird[birdstate].getWidth()-5, bird[birdstate].getHeight()-5 );

		for( int i=0;i<coinRect.size();i++)
		{
			if(Intersector.overlaps(birdReact,coinRect.get(i)))
			{
//				score++;
				gamestate=2;
//				coinRect.remove(i);
//				coinx.remove(i);
//				coiny.remove(i);
//				break;

				//Gdx.app.log("coin","collision");
			}
		}
		for( int i=0;i<bombRect.size();i++)
		{
			if(Intersector.overlaps(birdReact,bombRect.get(i)))
			{
				gamestate=2;
			}
		}


		font.draw(batch,String.valueOf(score),100 , 200);


		batch.end();
	}

}
