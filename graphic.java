package vor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;


public class graphic 
{
	
	Graphics2D g1;
	private graphic(Graphics g) 
	{
		this.g1 = (Graphics2D) g.create();
	}

	public static graphic create(Graphics g)
	{
		return new graphic(g);
	}
	
	public graphic draw_picture(ImageObserver context, String key, int x, int y)
	{
		BufferedImage picture = Components.picture(key);
		if (picture != null) 
		{
			g1.draw_picture(picture, x, y, context);
		}
		return this;
	}
	

	public graphic draw_pictureRotated(ImageObserver context, String key, int x, int y, double degrees)
	{
		BufferedImage picture = Components.picture(key);
		if (picture != null) 
		{
			double r = Math.toRadians(degrees);
			double w = picture.getWidth() / 2;
			double h = picture.getHeight() / 2;
			AffineTransform at = AffineTransform.getRotateInstance(r, w, h);
			AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			g1.draw_picture(op.filter(picture, null), x, y, context);
		}
		return this;
	}
	
public graphic drawRay(int x, int y, int length, double degrees, int thickness, Color color) 
	{
		g1.setStroke(new BasicStroke(thickness));
		g1.setColor(color);
		double r = Math.toRadians(degrees);
		int a = x + (int) (length * Math.cos(r));
		int b = y + (int) (length * Math.sin(r));
		g1.drawLine(x, y, a, b);
		return this;
	}
	
	public graphic drawText(ImageObserver context, String font, int x, int y, String text)
	{
		BufferedImage f = Components.picture(font);
		if (f != null) 
		{
			String fontmap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			text = text.toUpperCase();
			for (int i = 0; i < text.length(); i++) 
			{
				int token = fontmap.indexOf(text.charAt(i));
				if (token != -1) 
				{
					g1.draw_picture(f.getSubpicture(token * 25, 0, 25, 41), x, y, context);
				}
				x += 31;
			}
		}
		return this;
	}
	
	public void flush()
	{
		g1.dispose();
	}
}