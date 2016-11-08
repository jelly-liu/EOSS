package com.jelly.eoss.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class ICodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ICODE_SESSION_KEY = "ICODE_2012-12-23";
	private int width;
	private int height;
	private int length;
	private String[] charts;
	
	public ICodeServlet(){
		this.initIcodeServlet();
	}
	
	private void defaultConfig(){
		this.width = 100;
		this.height = 25;
		this.length = 4;
		this.charts = "a,0,k,l,b,1,m,n,c,2,o,p,d,3,q,r,e,4,s,t,f,5,u,v,g,6,w,x,h,7,y,z,i,8,j,9".split(",");
	}
	
	private void initIcodeServlet(){
		try{
			Properties config = new Properties();
			config.load(ICodeServlet.class.getResourceAsStream("/config.properties"));
			this.width = Integer.parseInt(config.getProperty("icode.width"));
			this.height = Integer.parseInt(config.getProperty("icode.height"));
			this.length = Integer.parseInt(config.getProperty("icode.length"));
			this.charts = config.getProperty("icode.text").split(",");
		}catch(Exception e){
			defaultConfig();
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = buffImg.createGraphics();
		Random random = new Random();
		
		//画淡色的背景
		g2d.setColor(this.getRandomColor(random, 200, 55));
		g2d.fillRect(0, 0, width, height);
		
		//画文字
		String randomString = this.getRandomString(random);
		g2d.setFont(new Font("system", Font.BOLD, 24));
		g2d.setColor(this.getRandomColor(random, 0, 150));
		g2d.drawString(randomString, random.nextInt(40), (this.height)/2 + 10);
		
		//画干扰线
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		for(int i = 0; i < 1; i++){
			g2d.setColor(this.getRandomColor(random, 0, 255));
			g2d.setStroke(new BasicStroke(2 ,BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
			x1 = random.nextInt(10);
			y1 = random.nextInt(this.height);
			x2 = this.width - 10 + random.nextInt(10);
			y2 = random.nextInt(this.height);
			g2d.drawLine(x1, y1, x2, y2);
			
			g2d.setColor(this.getRandomColor(random, 0, 255));
			g2d.drawArc(0, random.nextInt(this.height), this.width, this.height, 0, 100 + random.nextInt(80));
		}
		
		//画干扰点
		for(int i = 0; i < 4; i++){
			g2d.setColor(this.getRandomColor(random, 0, 255));
//			g2d.setStroke(new BasicStroke(1 ,BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
			x1 = random.nextInt(this.width);
			y1 = random.nextInt(this.height);
			g2d.fillRect(x1, y1, random.nextInt(10), random.nextInt(10));
		}
		
		//释放此图形的上下文以及它使用的所有系统资源
		g2d.dispose();
		
		//设置response类型
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		
		//输出图像
		ServletOutputStream os = response.getOutputStream();
		ImageIO.write(buffImg, "jpeg", os);
		
		os.close();
		
		//设置Session
		request.getSession().setAttribute(ICODE_SESSION_KEY, randomString);
	}
	
	private String getRandomString(Random random){
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < this.length; i++){
			sb.append(this.charts[random.nextInt(this.charts.length)]);
		}
		
		return sb.toString();
	}
	
	private Color getRandomColor(Random random, int start, int max){
		int r = start + random.nextInt(max);
		int g = start + random.nextInt(max);
		int b = start + random.nextInt(max);
		return new Color(r, g, b);
	}
}
