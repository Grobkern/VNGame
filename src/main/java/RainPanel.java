import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class RainPanel extends JPanel {
	private float mWind = 2.05f;
	private float mGravity = 9.8f;
	private double mRainChance = 0.99;
	private int mRepaintTimeMS = 16;
	private float mRainWidth=1;
	private double mDdropInitialVelocity = 20;
	private double mDropDiam = 2;
	private Color mColor=new Color(0, 0, 0,30);
	private String imageScenePath;

	private ArrayList<Rain> rainV;
	private ArrayList<Drop> dropV;
	private UpdateThread mUpdateThread;

	public RainPanel() {
	    rainV = new ArrayList<>();
	    dropV = new ArrayList<>();
		mUpdateThread=new UpdateThread();
	    mUpdateThread.start();
	}

	public void stop() {
	    mUpdateThread.stopped=true;
	}

	public boolean isStopped() {
		return mUpdateThread.stopped;
	}

	public int getHeight() {
	    return this.getSize().height;
	}

	public int getWidth() {
	    return this.getSize().width;
	}

	private class UpdateThread extends Thread {
	    public volatile boolean stopped=false;
	    @Override
	    public void run() {
	        while (!stopped) {
	        	RainPanel.this.setOpaque(true);
				RainPanel.this.setBackground(new Color(0,0,0,0));
	            RainPanel.this.repaint();
	            try {
	                Thread.sleep(mRepaintTimeMS);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(mRainWidth));
	    g2.setColor(mColor);
	    Iterator<Drop> iterator2 = dropV.iterator();
	    while (iterator2.hasNext()) {
	        Drop drop = iterator2.next();
	        drop.update();
	        drop.draw(g2);

	        if (drop.y >= getHeight()) {
	            iterator2.remove();
	        }
	    }
	    Iterator<Rain> iterator = rainV.iterator();
	    while (iterator.hasNext()) {
	        Rain rain = iterator.next();
	        rain.update();
	        rain.draw(g2);

	        if (rain.y >= getHeight()) {
	            long dropCount = 1 + Math.round(Math.random() * 4);
	            for (int i = 0; i < dropCount; i++) {
	                dropV.add(new Drop(rain.x, getHeight()));
	            }
	            iterator.remove();

	        }
	    }

	    if (Math.random() < mRainChance) {
	        rainV.add(new Rain());
	    } 
	}

	class Rain {
	    float x;
	    float y;
	    float prevX;
	    float prevY;

	    public Rain() {
	        Random r = new Random();
	        x = r.nextInt(getWidth());
	        y = 0;
	    }

	    public void update() {
	        prevX = x;
	        prevY = y;

	        x += mWind;
	        y += mGravity;
	    }

	    public void draw(Graphics2D g2) {
	        Line2D line = new Line2D.Double(x, y, prevX, prevY);
	        g2.draw(line);
	    }
	}


	private class Drop {

	    double x0;  
	    double y0;  
	    double v0; 
	    double t;  
	    double angle;
	    double x;
	    double y;

	    public Drop(double x0, double y0) {
	        super();
	        this.x0 = x0;
	        this.y0 = y0;

	        v0 = mDdropInitialVelocity;
	        angle = Math.toRadians(Math.round(Math.random() * 180)); 
	    }

	    private void update() {
	        t += mRepaintTimeMS / 1000f;
	        x = x0 + v0 * t * Math.cos(angle);
	        y = y0 - (v0 * t * Math.sin(angle) - mGravity * t * t / 2);
	    }

	    public void draw(Graphics2D g2) {
	        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, mDropDiam, mDropDiam);
	        g2.fill(circle);
	    }
	}
	}